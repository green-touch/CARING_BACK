package com.caring.user_service.presentation.sensor.dto.sensor;

import com.caring.user_service.presentation.sensor.dto.BaseReadingDto;
import lombok.Getter;

@Getter
public class ActivityReadingDto extends BaseReadingDto {
    private String state;
    private Integer confidence;
}
