package com.caring.user_service.presentation.sensor.controller;

import com.caring.user_service.presentation.sensor.dto.SensorDataRequestDto;
import com.caring.user_service.presentation.sensor.service.IngestEventUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "[센서 이벤트(AUTH)]")
@Slf4j
@RestController
@RequestMapping("/v1/api/sensors")
@RequiredArgsConstructor
public class SensorApiController {

    private final IngestEventUseCase ingestEventUseCase;

    @Operation(summary = "센서값들을 서버로 전송합니다.")
    @PostMapping
    public ResponseEntity<Void> receiveSensorData(@RequestBody SensorDataRequestDto sensorDataRequestDto) {
        ingestEventUseCase.execute(sensorDataRequestDto);
    }
}
