package com.caring.manager_service.presentation.manager.service;

import com.caring.manager_service.common.annotation.UseCase;
import com.caring.manager_service.common.util.RoleUtil;
import com.caring.manager_service.domain.authority.entity.SuperAuth;
import com.caring.manager_service.domain.manager.business.adaptor.ManagerAdaptor;
import com.caring.manager_service.domain.manager.business.validator.ManagerValidator;
import com.caring.manager_service.domain.manager.entity.Manager;
import com.caring.manager_service.infra.user.client.UserServiceClient;
import com.caring.manager_service.infra.user.vo.response.ResponseUserDetailInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@UseCase
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetUserInfoByManagerUseCase {

    private final ManagerValidator managerValidator;
    private final UserServiceClient userServiceClient;

    public ResponseUserDetailInfo execute(String managerCode, List<String> roles, String userCode) {
        ResponseUserDetailInfo userDetailInfo = userServiceClient.getUserDetailInfo(userCode);
        boolean groupedUser = managerValidator.isGroupedUser(managerCode, userDetailInfo.getUserUuid());
        boolean getAuth = RoleUtil.checkManagerRole(SuperAuth.GET_ALL_SENIOR_ACCOUNT, roles);
        if(!groupedUser && !getAuth) throw new RuntimeException("해당 노인 계정을 조회할 수 있는 권한이 없습니다.");

        return userDetailInfo;
    }
}
