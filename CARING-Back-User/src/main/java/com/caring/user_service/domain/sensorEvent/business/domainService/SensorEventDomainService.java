package com.caring.user_service.domain.sensorEvent.business.domainService;

import com.caring.user_service.domain.sensorEvent.entity.SensorEvent;

public interface SensorEventDomainService {

    SensorEvent createSensorEvent(String deviceId, String payloadJson);
}
