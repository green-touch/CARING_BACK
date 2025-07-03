package com.caring.manager_service.presentation.security.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RequestLogin {
    private final String memberCode;
    private final String password;
}
