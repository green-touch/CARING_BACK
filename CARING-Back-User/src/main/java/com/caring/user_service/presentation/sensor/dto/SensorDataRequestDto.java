package com.caring.user_service.presentation.sensor.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SensorDataRequestDto {
    private String deviceId;
    private Long sentAt;
    private List<BaseReadingDto> readings;
}
