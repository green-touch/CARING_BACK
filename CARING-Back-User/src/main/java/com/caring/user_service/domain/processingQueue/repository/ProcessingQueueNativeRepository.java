package com.caring.user_service.domain.processingQueue.repository;

import com.caring.user_service.domain.processingQueue.entity.ProcessingQueue;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProcessingQueueNativeRepository {
    private final JdbcTemplate jdbc;

    public record ProcessingJob(
            long pqId,
            String eventId,
            String deviceId,
            int attempt,
            LocalDateTime leaseUntil
    ) {}

    private static final RowMapper<ProcessingJob> JOB_MAPPER = (rs, i) -> new ProcessingJob(
            rs.getLong("pq_id"),
            rs.getString("event_id"),
            rs.getString("device_id"),
            rs.getInt("attempt"),
            toLdt(rs.getTimestamp("lease_until"))
    );

    private static LocalDateTime toLdt(Timestamp ts) {
        return ts == null ? null : ts.toLocalDateTime();
    }

    @Transactional
    public List<ProcessingJob> pickBatch(int limit, Duration lease, String workerId) {
        // 1) 후보 선택 + 행 잠금 (잠긴 행은 건너뜀)
        List<Long> ids = jdbc.queryForList("""
                SELECT pq_id
                  FROM processing_queue
                 WHERE status IN ('PENDING','FAILED')
                   AND next_run_at <= NOW(6)
                 ORDER BY next_run_at, pq_id
                 LIMIT ? FOR UPDATE SKIP LOCKED
                """, Long.class, limit);

        if (ids.isEmpty()) return List.of();

        // 2) 상태 전이 RUNNING (attempt++, lease_until, claimed_by)
        String inPh = placeholders(ids.size());
        List<Object> params = new ArrayList<>();
        params.add(lease.getSeconds());
        params.add(workerId);
        params.addAll(ids);

        jdbc.update("""
                        UPDATE processing_queue
                           SET status      = 'RUNNING',
                               attempt     = attempt + 1,
                               lease_until = DATE_ADD(NOW(6), INTERVAL ? SECOND),
                               claimed_by  = ?,
                               updated_at  = NOW(6)
                         WHERE pq_id IN (""" + inPh + ")",
                params.toArray()
        );

        // 3) 상세 반환
        List<Object> idParams = new ArrayList<>(ids);
        return jdbc.query("""
                        SELECT pq_id, event_id, device_id, attempt, lease_until
                          FROM processing_queue
                         WHERE pq_id IN (""" + inPh + ")",
                JOB_MAPPER,
                idParams.toArray()
        );
    }

    /** 성공 처리 */
    public void markDone(long pqId) {
        jdbc.update("""
                UPDATE processing_queue
                   SET status = 'DONE',
                       updated_at = NOW(6)
                 WHERE pq_id = ?
                """, pqId);
    }

    /** 실패 처리 + 백오프 재시도 예약(next_run_at) */
    public void markFailed(long pqId, String error, Duration backoff) {
        jdbc.update("""
                UPDATE processing_queue
                   SET status      = 'FAILED',
                       last_error  = LEFT(CONCAT(COALESCE(last_error,''),' | ', ?), 4000),
                       next_run_at = DATE_ADD(NOW(6), INTERVAL ? SECOND),
                       updated_at  = NOW(6)
                 WHERE pq_id = ?
                """, error, backoff.getSeconds(), pqId);
    }

    /** 최대 시도 초과/치명적 오류 등으로 데드레터 전환 */
    public void markDeadLetter(long pqId, String error) {
        jdbc.update("""
                UPDATE processing_queue
                   SET status     = 'DEAD_LETTER',
                       last_error = LEFT(CONCAT(COALESCE(last_error,''),' | ', ?), 4000),
                       updated_at = NOW(6)
                 WHERE pq_id = ?
                """, error, pqId);
    }

    /**
     * lease 만료 작업을 재큐잉(스턱 복구)
     * safety 초만큼 여유를 두고 만료된 RUNNING을 FAILED로 내려 재시도 대상으로 만듦
     */
    public int requeueExpiredLeases(Duration safety) {
        return jdbc.update("""
                UPDATE processing_queue
                   SET status      = 'FAILED',
                       next_run_at = NOW(6),
                       updated_at  = NOW(6),
                       last_error  = LEFT(CONCAT(COALESCE(last_error,''),' | lease expired'), 4000)
                 WHERE status = 'RUNNING'
                   AND lease_until < DATE_SUB(NOW(6), INTERVAL ? SECOND)
                """, safety.getSeconds());
    }

    /** 장기 작업 시 리스를 연장하고 싶을 때(옵션) */
    public int extendLease(long pqId, Duration extraTtl) {
        return jdbc.update("""
                UPDATE processing_queue
                   SET lease_until = DATE_ADD(COALESCE(lease_until, NOW(6)), INTERVAL ? SECOND),
                       updated_at  = NOW(6)
                 WHERE pq_id = ?
                   AND status = 'RUNNING'
                """, extraTtl.getSeconds(), pqId);
    }

    private static String placeholders(int n) {
        return String.join(",", Collections.nCopies(n, "?"));
    }
}
