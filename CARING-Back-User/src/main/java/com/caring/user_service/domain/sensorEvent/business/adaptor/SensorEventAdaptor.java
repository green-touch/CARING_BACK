package com.caring.user_service.domain.sensorEvent.business.adaptor;

import com.caring.user_service.domain.sensorEvent.entity.SensorEvent;

public interface SensorEventAdaptor {

    SensorEvent querySensorEventByEventId(Long eventId);
}
