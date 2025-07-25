package com.caring.manager_service.presentation.manager.service;

import com.caring.manager_service.common.annotation.UseCase;
import com.caring.manager_service.common.util.RoleUtil;
import com.caring.manager_service.domain.authority.entity.SuperAuth;
import com.caring.manager_service.domain.manager.business.service.ManagerDomainService;
import com.caring.manager_service.presentation.manager.vo.request.RequestManager;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@UseCase
@Transactional
@RequiredArgsConstructor
public class RegisterDefaultManagerBySuperManagerUseCase {

    private final ManagerDomainService managerDomainService;

    public Long execute(List<String> roles, RequestManager requestManager) {
        RoleUtil.containManagerRole(SuperAuth.CREATE_MANAGER_ACCOUNT, roles);
        return managerDomainService
                .registerManager(requestManager.getName(), requestManager.getPassword()).getId();
    }
}
