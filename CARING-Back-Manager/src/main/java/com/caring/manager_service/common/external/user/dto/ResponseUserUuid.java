package com.caring.manager_service.common.external.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseUserUuid {
    private Long userId;
    private String userUuid;
}

