package com.caring.user_service.presentation.user.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RequestMemberCode {
    private final String name;
    private final String birthDate;
    private final String phoneNumber;
}
