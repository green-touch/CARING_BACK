package com.caring.manager_service.common.util;

import com.caring.manager_service.domain.authority.entity.SuperAuth;

import java.util.List;

public class RoleUtil {

    public static void containManagerRole(SuperAuth superAuth, List<String> roles) {
        if (roles.stream()
                .map(role -> EnumConvertUtil.convert(SuperAuth.class, role))
                .noneMatch(superAuth::equals)) {
            throw new RuntimeException("No matching role found");
        }
    }

    public static void containManagerRole(List<SuperAuth> managerRoles, List<String> roles) {
        if (roles.stream()
                .map(role -> EnumConvertUtil.convert(SuperAuth.class, role))
                .noneMatch(managerRoles::contains)
        ) {
            throw new RuntimeException("No matching role found");
        }
    }

    public static boolean checkManagerRole(SuperAuth superAuth, List<String> roles) {
        if (roles.stream()
                .map(role -> EnumConvertUtil.convert(SuperAuth.class, role))
                .noneMatch(superAuth::equals)) {
            return false;
        }
        return true;
    }
}
