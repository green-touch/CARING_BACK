package com.caring.manager_service.presentation.auth.service;

import com.caring.manager_service.common.annotation.UseCase;
import com.caring.manager_service.domain.authority.business.adaptor.AuthorityAdaptor;
import com.caring.manager_service.domain.authority.business.service.AuthorityDomainService;
import com.caring.manager_service.domain.authority.entity.SuperAuth;
import com.caring.manager_service.domain.authority.entity.SuperAuthority;
import com.caring.manager_service.domain.manager.business.adaptor.ManagerAdaptor;
import com.caring.manager_service.domain.manager.entity.Manager;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@UseCase
@Transactional
@RequiredArgsConstructor
public class SwitchSuperAuthUseCase {

    private final ManagerAdaptor managerAdaptor;
    private final AuthorityAdaptor authorityAdaptor;
    private final AuthorityDomainService authorityDomainService;

    public void execute(String managerUuid, List<SuperAuth> activeSuperAuths){
        // 1. FIND MANAGER
        Manager manager = managerAdaptor.queryByManagerUuid(managerUuid);

        // 2. READY CUR_AUTH & ACTIVE_AUTH
        Set<SuperAuth> currentSuperAuth =
                authorityAdaptor.queryCurrentPersonalSuperAuthority(manager).stream()
                        .map(psa -> psa.getSuperAuthority().getSuperAuth())
                        .collect(Collectors.toSet());

        Set<SuperAuth> activeSuperAuthSet = new HashSet<>(activeSuperAuths);

        // BLACK LIST
        List<SuperAuth> blackList = currentSuperAuth.stream()
                .filter(csa -> !activeSuperAuthSet.contains(csa))
                .collect(Collectors.toList());

        // WHITE LIST
        List<SuperAuthority> whiteList = activeSuperAuthSet.stream()
                .filter(asa -> !currentSuperAuth.contains(asa))
                .map(asa -> authorityAdaptor.queryBySuperAuth(asa))
                .collect(Collectors.toList());

        authorityDomainService.saveAllActiveSuperAuthority(manager, whiteList);
        authorityDomainService.removeAllUnActiveSuperAuthority(manager, blackList);
    }
}
