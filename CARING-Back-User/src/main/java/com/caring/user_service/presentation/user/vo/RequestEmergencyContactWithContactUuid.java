package com.caring.user_service.presentation.user.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class RequestEmergencyContactWithContactUuid {

    private final String contactName;
    private final String contactRelationship;
    private final String contactPhoneNumber;
    private final String contactUuid;
}
