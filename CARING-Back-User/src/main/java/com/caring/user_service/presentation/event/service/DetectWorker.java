package com.caring.user_service.presentation.event.service;

import com.caring.user_service.common.annotation.UseCase;
import com.caring.user_service.domain.processingQueue.dto.ProcessingJob;
import com.caring.user_service.domain.processingQueue.repository.ProcessingQueueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

import java.net.InetAddress;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;

import static com.caring.user_service.common.util.WorkerUtil.*;

@UseCase
@RequiredArgsConstructor
public class DetectWorker {

    private final ProcessingQueueRepository processingQueueRepository;
    private final DetectEventUseCase detectEventUseCase;
    private static final long leaseSec = 30;

    @Scheduled(fixedDelayString = "${queue.poll.ms:2000}")
    public void drain() {
        List<ProcessingJob> jobList = processingQueueRepository.pickBatch(200, Duration.ofSeconds(leaseSec), workerId());
        jobList.forEach(job -> CompletableFuture.runAsync(() -> process(job), taskExecutor()));
    }

    @Scheduled(fixedDelayString = "${queue.lease.recover.ms:10000}")
    public void recoverStuck() {
        processingQueueRepository.requeueExpiredLeases(Duration.ofSeconds(5)); // 안전 여유
    }

    @Async("detectorPool")
    void process(ProcessingJob job) {
        try {
            detectEventUseCase.runDetectEvent(job.eventId());
            processingQueueRepository.markDone(job.pqId());
        } catch (Exception e) {
            processingQueueRepository.markFailed(job.pqId(), shortErr(e), backoff(job.attempt()+1));
        }
    }
}
