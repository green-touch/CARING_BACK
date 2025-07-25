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

    public static void validateAuthOfHandleUser(boolean groupedUser, SuperAuth superAuth, List<String> roles) {
        if(!groupedUser && !checkManagerRole(superAuth, roles)) {
            throw new RuntimeException("해당 노인 계정을 조회할 수 있는 권한이 없습니다.");
        }
    }
}
