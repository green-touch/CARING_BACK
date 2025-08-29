package com.caring.user_service.domain.command.entity;

import com.caring.user_service.domain.auditing.entity.BaseTimeEntity;
import com.caring.user_service.domain.sensorEvent.entity.CommandStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * topic : send event to client triggered event
 */
@Entity
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Commands extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "cmd_id")
    private Long id;
    private String deviceId;
    @Lob @Column(columnDefinition="json")
    private String payloadJson;
    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private CommandStatus commandStatus = CommandStatus.CREATED;
}
