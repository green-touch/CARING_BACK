package com.caring.manager_service.presentation.manager.service;

import com.caring.manager_service.common.annotation.UseCase;
import com.caring.manager_service.domain.authority.business.adaptor.AuthorityAdaptor;
import com.caring.manager_service.domain.authority.entity.ManagerRole;
import com.caring.manager_service.domain.manager.business.service.ManagerDomainService;
import com.caring.manager_service.presentation.manager.vo.request.RequestManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@UseCase
@Transactional
@RequiredArgsConstructor
public class RegisterManagerUseCase {

    private final ManagerDomainService managerDomainService;
    private final AuthorityAdaptor authorityAdaptor;

    public Long execute(RequestManager requestManager) {
        return managerDomainService.registerManager(
                requestManager.getName(),
                requestManager.getPassword(),
                authorityAdaptor.queryByManagerRole(ManagerRole.MANAGE)
                ).getId();
    }
}
