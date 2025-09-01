package com.caring.user_service.presentation.event.dto.sensor;

import com.caring.user_service.presentation.event.dto.BaseReadingDto;
import lombok.Getter;

@Getter
public class BatteryReadingDto extends BaseReadingDto {
    private Double level;
    private Boolean charging;
    private Boolean powerSave;
}
