package com.caring.user_service.domain.processingQueue.repository;

import java.time.Duration;
import java.util.List;

import static com.caring.user_service.domain.processingQueue.repository.ProcessingQueueNativeRepositoryImpl.*;

public interface ProcessingQueueNativeRepository {

    List<ProcessingJob> pickBatch(int limit, Duration lease, String workerId);

    void markDone(long pqId);

    void markFailed(long pqId, String error, Duration backoff);

    void markDeadLetter(long pqId, String error);

    int requeueExpiredLeases(Duration safety);

    int extendLease(long pqId, Duration extraTtl);
}
