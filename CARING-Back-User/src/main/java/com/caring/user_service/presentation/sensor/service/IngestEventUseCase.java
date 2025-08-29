package com.caring.user_service.presentation.sensor.service;

import com.caring.user_service.common.annotation.UseCase;
import com.caring.user_service.domain.processingQueue.business.domainService.ProcessingQueueDomainService;
import com.caring.user_service.domain.sensorEvent.business.domainService.SensorEventDomainService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
@Transactional
public class IngestEventUseCase {

    private SensorEventDomainService sensorEventDomainService;
    private ProcessingQueueDomainService processingQueueDomainService;

    public void execute(String eventId, String deviceId, String payload){
        //save event(raw)
        sensorEventDomainService.createSensorEvent(eventId, deviceId, payload);
        //enqueue
        processingQueueDomainService.createProcessingQueue(eventId, deviceId);
    }
}
