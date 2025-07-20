package com.caring.manager_service.infra.user.vo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ResponseUserDetailInfo {
    private final String name;
    private final String memberCode;
    private final String userUuid;
    private final String shelterUuid;
    private final String profileImageUrl;
    private final String postalCode;
    private final String roadAddress;
    private final String detailAddress;
    private final String birthDate;
    private final String phoneNumber;
    private final String memo;
    private final List<ResponseEmergencyContact> emergencyContacts;
}
