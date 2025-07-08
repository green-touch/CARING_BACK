package com.caring.manager_service.domain.authority.entity;

public enum SuperAuth {
    /**
     * 해당 복지관 Super 매니저 권한 부여
     */
    GRANT_SUPER_MANAGER_PERMISSION,

    /**
     * 복지관 계정 수정 요청
     */
    REQUEST_WELFARE_CENTER_ACCOUNT_MODIFICATION,

    /**
     * 해당 복지관 매니저 계정 생성
     */
    CREATE_MANAGER_ACCOUNT,

    /**
     * 해당 복지관 매니저 계정 삭제
     */
    DELETE_MANAGER_ACCOUNT,

    /**
     * 담당 노인 추가 배정 / 삭제
     */
    ASSIGN_OR_DELETE_SENIOR_ALLOCATION,

    /**
     * 담당 노인 계정 추가 / 삭제
     */
    CREATE_OR_DELETE_SENIOR_ACCOUNT
}
