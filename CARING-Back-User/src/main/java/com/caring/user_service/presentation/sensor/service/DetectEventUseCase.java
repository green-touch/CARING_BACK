package com.caring.user_service.presentation.sensor.service;

import com.caring.user_service.common.annotation.UseCase;
import com.caring.user_service.domain.command.business.domainService.CommandsDomainService;
import com.caring.user_service.domain.sensorEvent.business.adaptor.SensorEventAdaptor;
import com.caring.user_service.domain.sensorEvent.entity.SensorEvent;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UseCase
@Transactional
@RequiredArgsConstructor
public class DetectEventUseCase {

    private final SensorEventAdaptor sensorEventAdaptor;
    private final CommandsDomainService commandDomainService;

    public void runDetectEvent(Long eventId){
        SensorEvent sensorEvent = sensorEventAdaptor.querySensorEventByEventId(eventId);
        boolean triggered = simpleFallRule(sensorEvent.getPayloadJson());
        if(!triggered) return;

        //TODO : make json by triggered event
        String payloadJson = "payloadJson";
        commandDomainService.createCommand(sensorEvent.getDeviceId(), payloadJson);
    }

    //TODO calc event by payload json
    private boolean simpleFallRule(String payloadJson) {
        log.info("payloadJson: {}", payloadJson);
        return false;
    }
}
