package com.caring.user_service.presentation.event.dto.sensor;

import com.caring.user_service.presentation.event.dto.BaseReadingDto;
import lombok.Getter;

@Getter
public class VectorReadingDto extends BaseReadingDto {
    private Double x;
    private Double y;
    private Double z;
}
