package com.caring.user_service.presentation.sensor.dto.sensor;

import com.caring.user_service.presentation.sensor.dto.BaseReadingDto;
import lombok.Getter;

@Getter
public class VectorReadingDto extends BaseReadingDto {
    private Double x;
    private Double y;
    private Double z;
}
