package com.caring.manager_service.domain.authority.business.service;

import com.caring.manager_service.domain.authority.entity.SuperAuth;
import com.caring.manager_service.domain.authority.entity.SuperAuthority;
import com.caring.manager_service.domain.manager.entity.Manager;

import java.util.List;

public interface AuthorityDomainService {

    void saveAllActiveSuperAuthority(Manager manager, List<SuperAuthority> activeSuperAuthorityList);

    void removeAllUnActiveSuperAuthority(Manager manager, List<SuperAuth> unactiveSuperAuthList);
}
