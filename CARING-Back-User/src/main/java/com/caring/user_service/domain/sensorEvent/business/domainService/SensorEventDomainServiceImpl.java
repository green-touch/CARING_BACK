package com.caring.user_service.domain.sensorEvent.business.domainService;

import com.caring.user_service.common.annotation.DomainService;
import com.caring.user_service.domain.sensorEvent.entity.SensorEvent;
import com.caring.user_service.domain.sensorEvent.repository.SensorEventRepository;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class SensorEventDomainServiceImpl implements SensorEventDomainService {

    private final SensorEventRepository sensorEventRepository;
    @Override
    public SensorEvent createSensorEvent(String deviceId, String payloadJson) {
        SensorEvent sensorEvent = SensorEvent.builder()
                .deviceId(deviceId)
                .payloadJson(payloadJson)
                .build();
        return sensorEventRepository.save(sensorEvent);
    }
}
