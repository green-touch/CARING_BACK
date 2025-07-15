package com.caring.manager_service.presentation.user.service;

import com.caring.manager_service.common.annotation.UseCase;
import com.caring.manager_service.domain.manager.business.adaptor.ManagerAdaptor;
import com.caring.manager_service.domain.manager.business.service.ManagerDomainService;
import com.caring.manager_service.domain.manager.entity.Manager;
import com.caring.manager_service.infra.user.client.UserServiceClient;
import com.caring.manager_service.infra.user.vo.request.RequestUserWithShelterUuid;
import com.caring.manager_service.infra.user.vo.response.ResponseUserUuid;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class RegisterUserAccountByManagerUseCase {

    private final UserServiceClient userServiceClient;
    private final ManagerDomainService managerDomainService;
    private final ManagerAdaptor managerAdaptor;

    public ResponseUserUuid execute(String managerCode,
                                    Long shelterId,
                                    RequestUserWithShelterUuid requestUserWithShelterUuid) {
        Manager manager = managerAdaptor.queryByMemberCode(managerCode);
        // TODO: manager와 shelter 소속 검증
        // TODO: user에 shelter 소속시키기
        ResponseUserUuid responseUserUuid = userServiceClient.registerUser(requestUserWithShelterUuid);
        managerDomainService.addUserToManagerGroup(manager, responseUserUuid.getUserUuid());
        return responseUserUuid;
    }
}
