package com.caring.manager_service.presentation.user.service;

import com.caring.manager_service.common.annotation.UseCase;
import com.caring.manager_service.common.util.RoleUtil;
import com.caring.manager_service.domain.authority.entity.SuperAuth;
import com.caring.manager_service.domain.manager.business.adaptor.ManagerAdaptor;
import com.caring.manager_service.domain.manager.business.adaptor.ManagerGroupAdaptor;
import com.caring.manager_service.domain.manager.business.validator.ManagerValidator;
import com.caring.manager_service.domain.manager.entity.Manager;
import com.caring.manager_service.infra.user.client.UserServiceClient;
import com.caring.manager_service.infra.user.vo.response.ResponseUserDetailInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.caring.manager_service.common.util.RoleUtil.validateAuthOfHandleUser;
import static com.caring.manager_service.domain.authority.entity.SuperAuth.GET_ALL_SENIOR_ACCOUNT;

@UseCase
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetUserDetailInfoByManagerUseCase {

    private final UserServiceClient userServiceClient;
    private final ManagerValidator managerValidator;

    public ResponseUserDetailInfo execute(String managerCode, List<String> roles, String userUuid) {
        validateAuthOfHandleUser(
                managerValidator.isGroupedUser(managerCode, userUuid),
                GET_ALL_SENIOR_ACCOUNT,
                roles
        );
        return userServiceClient.getUserDetailInfo(userUuid);
    }
}
