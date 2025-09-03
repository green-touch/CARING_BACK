package com.caring.user_service.presentation.sensor.dto.sensor;

import com.caring.user_service.presentation.sensor.dto.BaseReadingDto;
import lombok.Getter;

@Getter
public class ValueReadingDto extends BaseReadingDto {
    private Double value;
}
