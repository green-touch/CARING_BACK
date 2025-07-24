package com.caring.manager_service.infra.user.vo.request;

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
