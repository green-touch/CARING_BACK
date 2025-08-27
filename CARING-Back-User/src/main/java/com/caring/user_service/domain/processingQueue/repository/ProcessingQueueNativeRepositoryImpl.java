package com.caring.user_service.domain.processingQueue.repository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static com.caring.user_service.common.consts.NativeQuery.*;

@Repository
@RequiredArgsConstructor
public class ProcessingQueueNativeRepositoryImpl implements ProcessingQueueNativeRepository {
    private final NamedParameterJdbcTemplate jdbc;

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

    @Override
    @Transactional
    public List<ProcessingJob> pickBatch(int limit, Duration lease, String workerId) {
        // 1) 후보 선택 + 행 잠금 (잠긴 행은 건너뜀)
        MapSqlParameterSource selectParams = new MapSqlParameterSource()
                .addValue("limit", limit);

        List<Long> ids = jdbc.query(SQL_SELECT_IDS_FOR_UPDATE, selectParams,
                (rs, i) -> rs.getLong("pq_id"));

        if (ids.isEmpty()) {
            return Collections.emptyList();
        }

        // 2) 상태 전이 RUNNING (attempt++, lease_until, claimed_by)
        MapSqlParameterSource updateParams = new MapSqlParameterSource()
                .addValue("leaseSeconds", lease.getSeconds())
                .addValue("workerId", workerId)
                .addValue("ids", ids);

        jdbc.update(SQL_UPDATE_TO_RUNNING, updateParams);

        // 3) 상세 반환
        MapSqlParameterSource fetchParams = new MapSqlParameterSource()
                .addValue("ids", ids);

        return jdbc.query(SQL_FETCH_PICKED_ROWS, fetchParams, JOB_MAPPER);
    }

    /** 성공 처리 */
    @Override
    public void markDone(long pqId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", pqId);
        jdbc.update(SQL_MARK_DONE, params);
    }

    /** 실패 처리 + 백오프 재시도 예약(next_run_at) */
    @Override
    public void markFailed(long pqId, String error, Duration backoff) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", pqId)
                .addValue("err", error)
                .addValue("backoffSeconds", backoff.getSeconds());
        jdbc.update(SQL_MARK_FAILED, params);
    }

    /** 최대 시도 초과/치명적 오류 등으로 데드레터 전환 */
    @Override
    public void markDeadLetter(long pqId, String error) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", pqId)
                .addValue("err", error);
        jdbc.update(SQL_MARK_DEAD, params);
    }

    /**
     * lease 만료 작업을 재큐잉(스턱 복구)
     * safety 초만큼 여유를 두고 만료된 RUNNING을 FAILED로 내려 재시도 대상으로 만듦
     */
    @Override
    public int requeueExpiredLeases(Duration safety) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("safetySeconds", safety.getSeconds());
        return jdbc.update(SQL_REQUEUE_EXPIRED, params);
    }

    /** 장기 작업 시 리스를 연장하고 싶을 때(옵션) */
    @Override
    public int extendLease(long pqId, Duration extraTtl) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", pqId)
                .addValue("extraSeconds", extraTtl.getSeconds());
        return jdbc.update(SQL_EXTEND_LEASE, params);
    }
}
