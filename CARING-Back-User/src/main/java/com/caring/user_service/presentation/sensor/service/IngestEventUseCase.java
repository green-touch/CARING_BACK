package com.caring.user_service.presentation.sensor.service;

import com.caring.user_service.common.annotation.UseCase;
import com.caring.user_service.domain.processingQueue.business.domainService.ProcessingQueueDomainService;
import com.caring.user_service.domain.sensorEvent.business.domainService.SensorEventDomainService;
import com.caring.user_service.domain.sensorEvent.entity.SensorEvent;
import com.caring.user_service.presentation.sensor.dto.SensorDataRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@UseCase
@Transactional
@RequiredArgsConstructor
public class IngestEventUseCase {

    private final SensorEventDomainService sensorEventDomainService;
    private final ProcessingQueueDomainService processingQueueDomainService;
    private final ObjectMapper objectMapper;

    public void execute(SensorDataRequestDto sensorDataRequestDto) {
        if (sensorDataRequestDto.getReadings() == null || sensorDataRequestDto.getReadings() == null) {
            throw new RuntimeException("cannot null sensor readings");
        }

        String payload;
        try {
            payload = objectMapper.writeValueAsString(sensorDataRequestDto.getReadings());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        //save event(raw)
        SensorEvent sensorEvent = sensorEventDomainService.createSensorEvent(sensorDataRequestDto.getDeviceId(), payload);
        //enqueue
        processingQueueDomainService.createProcessingQueue(sensorEvent, sensorDataRequestDto.getDeviceId());
    }
}
