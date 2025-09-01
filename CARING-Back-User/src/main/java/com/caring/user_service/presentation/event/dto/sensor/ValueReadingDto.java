package com.caring.user_service.presentation.event.dto.sensor;

import com.caring.user_service.presentation.event.dto.BaseReadingDto;
import lombok.Getter;

@Getter
public class ValueReadingDto extends BaseReadingDto {
    private Double value;
}
