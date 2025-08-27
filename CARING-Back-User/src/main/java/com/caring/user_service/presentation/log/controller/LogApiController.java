package com.caring.user_service.presentation.log.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "[로그(AUTH)]")
@Slf4j
@RestController
@RequestMapping("/v1/api/logs")
@RequiredArgsConstructor
public class LogApiController {

    @Operation(summary = "배터리 상태 로그 정보를 서버로 전송합니다.")
    @PostMapping("/battery-status")
    public void sendBatteryStatusInfo() {

    }

    @Operation(summary = "이상 현상 감지 센서값들을 서버로 전송합니다.")
    @PostMapping("/sensor")
    public void sendSensorInfo() {
    }
}
