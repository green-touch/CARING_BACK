package com.caring.manager_service.presentation.manager.service;

import com.caring.manager_service.common.annotation.UseCase;
import com.caring.manager_service.domain.manager.business.service.ManagerDomainService;
import com.caring.manager_service.presentation.manager.vo.request.RequestManager;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class RegisterDefaultManagerBySuperManagerUseCase {

    private final ManagerDomainService managerDomainService;

    public Long execute(RequestManager requestManager) {
        return managerDomainService
                .registerManager(requestManager.getName(), requestManager.getPassword()).getId();
    }
}
