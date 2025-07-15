package com.caring.user_service.presentation.user.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ResponseUser {
    private final String name;
    private final String memberCode;
    private final String userUuid;
    private final String shelterUuid;
    private final String profileImageUrl;
    private final List<ResponseEmergencyContact> emergencyContacts;
}
