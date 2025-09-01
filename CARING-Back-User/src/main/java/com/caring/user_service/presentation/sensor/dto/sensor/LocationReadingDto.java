package com.caring.user_service.presentation.sensor.dto.sensor;

import com.caring.user_service.presentation.sensor.dto.BaseReadingDto;
import lombok.Getter;

@Getter
public class LocationReadingDto extends BaseReadingDto {
    private Double lat;
    private Double lon;
    private Double acc;
    private Double alt;
    private Double speed;
    private String provider;
}
