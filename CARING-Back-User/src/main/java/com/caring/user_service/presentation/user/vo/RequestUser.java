package com.caring.user_service.presentation.user.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RequestUser {
    private final String name;
    private final String password;
    private final String phoneNumber;
    private final String birthdate;
    private final String roadAddress;
    private final String detailAddress;
}
