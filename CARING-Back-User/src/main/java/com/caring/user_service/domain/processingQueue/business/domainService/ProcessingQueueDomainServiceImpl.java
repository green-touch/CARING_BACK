package com.caring.user_service.domain.processingQueue.business.domainService;

import com.caring.user_service.common.annotation.DomainService;
import com.caring.user_service.domain.processingQueue.entity.ProcessStatus;
import com.caring.user_service.domain.processingQueue.entity.ProcessingQueue;
import com.caring.user_service.domain.processingQueue.repository.ProcessingQueueRepository;
import com.caring.user_service.domain.sensorEvent.entity.SensorEvent;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class ProcessingQueueDomainServiceImpl implements ProcessingQueueDomainService {

    private final ProcessingQueueRepository processingQueueRepository;

    @Override
    public ProcessingQueue createProcessingQueue(SensorEvent sensorEvent, String deviceId) {
        ProcessingQueue processingQueue = ProcessingQueue.builder()
                .deviceId(deviceId)
                .sensorEvent(sensorEvent)
                .status(ProcessStatus.PENDING)
                .build();

        return processingQueueRepository.save(processingQueue);
    }
}
