package com.caring.user_service.presentation.event.dto.sensor;

import com.caring.user_service.presentation.event.dto.BaseReadingDto;
import lombok.Getter;

@Getter
public class ActivityReadingDto extends BaseReadingDto {
    private String state;
    private Integer confidence;
}
