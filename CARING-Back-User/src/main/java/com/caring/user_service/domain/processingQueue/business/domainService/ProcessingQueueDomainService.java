package com.caring.user_service.domain.processingQueue.business.domainService;

import com.caring.user_service.domain.processingQueue.entity.ProcessingQueue;
import com.caring.user_service.domain.sensorEvent.entity.SensorEvent;

public interface ProcessingQueueDomainService {

    ProcessingQueue createProcessingQueue(SensorEvent sensorEvent, String deviceId);
}
