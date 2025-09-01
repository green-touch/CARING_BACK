package com.caring.user_service.presentation.sensor.service;

import com.caring.user_service.common.annotation.UseCase;
import com.caring.user_service.domain.processingQueue.business.domainService.ProcessingQueueDomainService;
import com.caring.user_service.domain.sensorEvent.business.domainService.SensorEventDomainService;
import com.caring.user_service.domain.sensorEvent.entity.SensorEvent;
import com.caring.user_service.presentation.sensor.dto.SensorDataRequestDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@UseCase
@Transactional
@RequiredArgsConstructor
public class IngestEventUseCase {

    private final SensorEventDomainService sensorEventDomainService;
    private final ProcessingQueueDomainService processingQueueDomainService;

    public void execute(SensorDataRequestDto sensorDataRequestDto) {
        //save event(raw)
        SensorEvent sensorEvent = sensorEventDomainService.createSensorEvent(sensorDataRequestDto.getDeviceId(), sensorDataRequestDto.getReadings().toString());
        //enqueue
        processingQueueDomainService.createProcessingQueue(sensorEvent, sensorDataRequestDto.getDeviceId());
    }
}
