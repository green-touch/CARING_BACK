package com.caring.user_service.domain.sensorEvent.entity;

import com.caring.user_service.domain.auditing.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SensorEvent extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "se_id")
    private Long id;
    private String eventId;
    private String deviceId;
    @Lob @Column(columnDefinition="json")
    private String payloadJson;
}
