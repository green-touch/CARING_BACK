package com.caring.manager_service.domain.authority.converter;

import com.caring.manager_service.domain.authority.entity.DefaultAuth;
import com.caring.manager_service.domain.authority.entity.DefaultAuthority;
import com.caring.manager_service.domain.authority.entity.SuperAuth;
import com.caring.manager_service.domain.authority.entity.SuperAuthority;

public class SuperAuthConverter {
    public static SuperAuthority toSuperAuthority(SuperAuth superAuth) {
        return SuperAuthority.builder()
                .superAuth(superAuth)
                .build();
    }
}
