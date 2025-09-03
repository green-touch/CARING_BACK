package com.caring.user_service.domain.sensorEvent.business.adaptor;

import com.caring.user_service.common.annotation.Adaptor;
import com.caring.user_service.domain.sensorEvent.entity.SensorEvent;
import com.caring.user_service.domain.sensorEvent.repository.SensorEventRepository;
import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class SensorEventAdaptorImpl implements SensorEventAdaptor{

    private final SensorEventRepository sensorEventRepository;
    @Override
    public SensorEvent querySensorEventByEventId(Long eventId) {
        return sensorEventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("not found sensor event"));
    }
}
