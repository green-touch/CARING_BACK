package com.caring.manager_service.domain.authority.converter;

import com.caring.manager_service.domain.authority.entity.DefaultAuth;
import com.caring.manager_service.domain.authority.entity.DefaultAuthority;

public class DefaultAuthConverter {
    public static DefaultAuthority toDefaultAuthority(DefaultAuth defaultAuthEnum) {
        return DefaultAuthority.builder()
                .defaultAuth(defaultAuthEnum)
                .build();
    }
}
