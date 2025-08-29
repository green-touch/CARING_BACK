package com.caring.user_service.domain.processingQueue.business.domainService;

import com.caring.user_service.domain.processingQueue.entity.ProcessingQueue;

public interface ProcessingQueueDomainService {

    ProcessingQueue createProcessingQueue(String eventId, String deviceId);
}
