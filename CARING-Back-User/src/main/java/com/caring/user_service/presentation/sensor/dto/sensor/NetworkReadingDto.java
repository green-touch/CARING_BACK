package com.caring.user_service.presentation.sensor.dto.sensor;

import com.caring.user_service.presentation.sensor.dto.BaseReadingDto;
import lombok.Getter;

@Getter
public class NetworkReadingDto extends BaseReadingDto {
    private Boolean online;
    private String type;
}
