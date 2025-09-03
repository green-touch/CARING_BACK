package com.caring.user_service.domain.processingQueue.dto;

import java.time.LocalDateTime;

public record ProcessingJob(
        long pqId,
        Long eventId,
        String deviceId,
        int attempt,
        LocalDateTime leaseUntil
) {}
