package com.caring.manager_service.domain.authority.entity;

import com.caring.manager_service.common.interfaces.KeyedEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ManagerRole implements KeyedEnum {
    //admin role 추가
    MANAGE("ROLE_MANAGE"), SUPER("ROLE_SUPER"), ADMIN("ROLE_ADMIN");

    private final String key;
}
