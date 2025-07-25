package com.caring.manager_service.infra.user.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class RequestEmergencyContact {

    private final String memberCode; // 유저 memberCode
    private final String contactName;
    private final String contactRelationship;
    private final String contactPhoneNumber;
}
