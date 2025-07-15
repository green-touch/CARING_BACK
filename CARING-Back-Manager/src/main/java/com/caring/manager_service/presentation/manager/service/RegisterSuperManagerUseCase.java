package com.caring.manager_service.presentation.manager.service;

import com.caring.manager_service.common.annotation.UseCase;
import com.caring.manager_service.domain.authority.business.adaptor.AuthorityAdaptor;
import com.caring.manager_service.domain.authority.business.service.AuthorityDomainService;
import com.caring.manager_service.domain.authority.entity.SuperAuthority;
import com.caring.manager_service.domain.manager.business.service.ManagerDomainService;
import com.caring.manager_service.domain.manager.entity.Manager;
import com.caring.manager_service.presentation.manager.vo.request.RequestManager;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@UseCase
@Transactional
@RequiredArgsConstructor
public class RegisterSuperManagerUseCase {

    private final ManagerDomainService managerDomainService;
    private final AuthorityAdaptor authorityAdaptor;
    private final AuthorityDomainService authorityDomainService;

    public Long execute(RequestManager requestManager) {
        Manager manager = managerDomainService
                .registerManager(requestManager.getName(), requestManager.getPassword());
        List<SuperAuthority> superAuthorities = authorityAdaptor.queryAllSuperAuthority();
        authorityDomainService.saveAllActiveSuperAuthority(manager, superAuthorities);
        return manager.getId();
    }
}
