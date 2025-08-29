package com.caring.user_service.presentation.event.service;

import com.caring.user_service.common.annotation.UseCase;
import com.caring.user_service.domain.processingQueue.business.domainService.ProcessingQueueDomainService;
import com.caring.user_service.domain.sensorEvent.business.domainService.SensorEventDomainService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@UseCase
@Transactional
@RequiredArgsConstructor
public class IngestEventUseCase {

    private final SensorEventDomainService sensorEventDomainService;
    private final ProcessingQueueDomainService processingQueueDomainService;

    public void execute(String eventId, String deviceId, String payload){
        //save event(raw)
        sensorEventDomainService.createSensorEvent(eventId, deviceId, payload);
        //enqueue
        processingQueueDomainService.createProcessingQueue(eventId, deviceId);
    }
}
