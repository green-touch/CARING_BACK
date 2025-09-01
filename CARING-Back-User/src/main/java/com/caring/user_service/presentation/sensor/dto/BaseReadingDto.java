package com.caring.user_service.presentation.sensor.dto;

import com.caring.user_service.presentation.sensor.dto.sensor.*;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;

@Getter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "kind")
@JsonSubTypes({
        @JsonSubTypes.Type(value = VectorReadingDto.class, name = "accelerometer"),
        @JsonSubTypes.Type(value = VectorReadingDto.class, name = "gyroscope"),
        @JsonSubTypes.Type(value = VectorReadingDto.class, name = "magnetometer"),
        @JsonSubTypes.Type(value = ValueReadingDto.class, name = "barometer"),
        @JsonSubTypes.Type(value = ValueReadingDto.class, name = "light"),
        @JsonSubTypes.Type(value = ValueReadingDto.class, name = "proximity"),
        @JsonSubTypes.Type(value = ValueReadingDto.class, name = "steps"),
        @JsonSubTypes.Type(value = LocationReadingDto.class, name = "location"),
        @JsonSubTypes.Type(value = BatteryReadingDto.class, name = "battery"),
        @JsonSubTypes.Type(value = NetworkReadingDto.class, name = "network"),
        @JsonSubTypes.Type(value = ScreenReadingDto.class, name = "screen"),
        @JsonSubTypes.Type(value = AppStateReadingDto.class, name = "appstate"),
        @JsonSubTypes.Type(value = ActivityReadingDto.class, name = "activity")
})
public abstract class BaseReadingDto {
    private String id;
    private String deviceId;
    private String kind;
    private Long ts;
}
