package com.caring.manager_service.presentation.user.service;

import com.caring.manager_service.common.annotation.UseCase;
import com.caring.manager_service.domain.manager.business.adaptor.ManagerAdaptor;
import com.caring.manager_service.domain.manager.business.adaptor.ManagerGroupAdaptor;
import com.caring.manager_service.domain.manager.entity.Manager;
import com.caring.manager_service.infra.user.client.UserServiceClient;
import com.caring.manager_service.infra.user.vo.response.ResponseUser;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@UseCase
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetUserAccountsInChargeUseCase {

    private final ManagerGroupAdaptor managerGroupAdaptor;
    private final UserServiceClient userServiceClient;

    public List<ResponseUser> execute(String managerCode) {
        List<String> userUuidListByManagerCode =
                managerGroupAdaptor.queryUserUuidListByManagerCode(managerCode);
        return userServiceClient.queryUserByUuidList(userUuidListByManagerCode);
    }
}
