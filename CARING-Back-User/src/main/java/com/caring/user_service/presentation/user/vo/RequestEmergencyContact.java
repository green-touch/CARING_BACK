package com.caring.user_service.presentation.user.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestEmergencyContact {

    private String memberCode; // 유저 memberCode
    private String contactName;
    private String contactRelationship;
    private String contactPhoneNumber;
}

