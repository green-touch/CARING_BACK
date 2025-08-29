package com.caring.user_service.presentation.event.service;

import com.caring.user_service.common.annotation.UseCase;
import com.caring.user_service.domain.processingQueue.business.domainService.ProcessingQueueDomainService;
import com.caring.user_service.domain.sensorEvent.business.domainService.SensorEventDomainService;
import com.caring.user_service.domain.sensorEvent.entity.SensorEvent;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@UseCase
@Transactional
@RequiredArgsConstructor
public class IngestEventUseCase {

    private final SensorEventDomainService sensorEventDomainService;
    private final ProcessingQueueDomainService processingQueueDomainService;

    public void execute(String deviceId, String payload){
        //save event(raw)
        SensorEvent sensorEvent = sensorEventDomainService.createSensorEvent(deviceId, payload);
        //enqueue
        processingQueueDomainService.createProcessingQueue(sensorEvent, deviceId);
    }
}
