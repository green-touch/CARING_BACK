package com.caring.user_service.common.consts;

public class NativeQuery {

    public static final String SQL_SELECT_IDS_FOR_UPDATE = """
            SELECT pq_id
              FROM processing_queue
             WHERE status IN ('PENDING','FAILED')
               AND next_run_at <= NOW(6)
             ORDER BY next_run_at, pq_id
             LIMIT :limit FOR UPDATE SKIP LOCKED
            """;

    public static final String SQL_UPDATE_TO_RUNNING = """
            UPDATE processing_queue
               SET status      = 'RUNNING',
                   attempt     = attempt + 1,
                   lease_until = DATE_ADD(NOW(6), INTERVAL :leaseSeconds SECOND),
                   claimed_by  = :workerId,
                   last_modified_date  = NOW(6)
             WHERE pq_id IN (:ids)
            """;
    public static final String SQL_FETCH_PICKED_ROWS = """
            SELECT pq_id, event_id, device_id, attempt, lease_until
              FROM processing_queue
             WHERE pq_id IN (:ids)
            """;

    public static final String SQL_MARK_DONE = """
            UPDATE processing_queue
               SET status = 'DONE',
                   last_modified_date = NOW(6)
             WHERE pq_id = :id
            """;

    public static final String SQL_MARK_FAILED = """
            UPDATE processing_queue
               SET status      = 'FAILED',
                   last_error  = LEFT(CONCAT(COALESCE(last_error,''),' | ', :err), 4000),
                   next_run_at = DATE_ADD(NOW(6), INTERVAL :backoffSeconds SECOND),
                   last_modified_date  = NOW(6)
             WHERE pq_id = :id
            """;

    public static final String SQL_MARK_DEAD = """
            UPDATE processing_queue
               SET status     = 'DEAD_LETTER',
                   last_error = LEFT(CONCAT(COALESCE(last_error,''),' | ', :err), 4000),
                   last_modified_date = NOW(6)
             WHERE pq_id = :id
            """;


    public static final String SQL_REQUEUE_EXPIRED = """
            UPDATE processing_queue
               SET status      = 'FAILED',
                   next_run_at = NOW(6),
                   last_modified_date  = NOW(6),
                   last_error  = LEFT(CONCAT(COALESCE(last_error,''),' | lease expired'), 4000)
             WHERE status = 'RUNNING'
               AND lease_until < DATE_SUB(NOW(6), INTERVAL :safetySeconds SECOND)
            """;

    public static final String SQL_EXTEND_LEASE = """
            UPDATE processing_queue
               SET lease_until = DATE_ADD(COALESCE(lease_until, NOW(6)), INTERVAL :extraSeconds SECOND),
                   last_modified_date  = NOW(6)
             WHERE pq_id = :id
               AND status = 'RUNNING'
            """;

}
