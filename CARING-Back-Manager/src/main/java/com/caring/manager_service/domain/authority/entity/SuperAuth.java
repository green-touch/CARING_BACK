package com.caring.manager_service.domain.authority.entity;

import com.caring.manager_service.common.interfaces.KeyedEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuperAuth implements KeyedEnum {
    /**
     * 해당 복지관 Super 매니저 권한 부여
     */
    GRANT_SUPER_MANAGER_PERMISSION("GRANT_SUPER_MANAGER_PERMISSION"),

    /**
     * 복지관 계정 수정 요청
     */
    REQUEST_WELFARE_CENTER_ACCOUNT_MODIFICATION("REQUEST_WELFARE_CENTER_ACCOUNT_MODIFICATION"),

    /**
     * 해당 복지관 매니저 계정 생성
     */
    CREATE_MANAGER_ACCOUNT("CREATE_MANAGER_ACCOUNT"),

    /**
     * 해당 복지관 매니저 계정 삭제
     */
    DELETE_MANAGER_ACCOUNT("DELETE_MANAGER_ACCOUNT"),

    /**
     * 담당 노인 추가 배정 / 삭제
     */
    ASSIGN_OR_DELETE_SENIOR_ALLOCATION("ASSIGN_OR_DELETE_SENIOR_ALLOCATION"),

    /**
     * 담당 노인 계정 추가 / 삭제
     */
    CREATE_OR_DELETE_SENIOR_ACCOUNT("CREATE_OR_DELETE_SENIOR_ACCOUNT"),

    /**
     * 모든 노인 계정 조회
     */
    GET_ALL_SENIOR_ACCOUNT("GET_ALL_SENIOR_ACCOUNT");

    private final String key;
}
