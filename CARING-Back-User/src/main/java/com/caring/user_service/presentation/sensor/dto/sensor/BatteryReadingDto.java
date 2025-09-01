package com.caring.user_service.presentation.sensor.dto.sensor;

import com.caring.user_service.presentation.sensor.dto.BaseReadingDto;
import lombok.Getter;

@Getter
public class BatteryReadingDto extends BaseReadingDto {
    private Double level;
    private Boolean charging;
    private Boolean powerSave;
}
