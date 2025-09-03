package com.caring.manager_service.infra.user.vo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RequestPhoneNumber {
    private final String phoneNumber;
}

