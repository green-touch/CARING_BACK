package com.caring.manager_service.domain.authority.business.adaptor;

import com.caring.manager_service.common.annotation.Adaptor;
import com.caring.manager_service.domain.authority.entity.PersonalSuperAuthority;
import com.caring.manager_service.domain.authority.entity.SuperAuth;
import com.caring.manager_service.domain.authority.entity.SuperAuthority;
import com.caring.manager_service.domain.authority.repository.PersonalSuperAuthorityRepository;
import com.caring.manager_service.domain.authority.repository.SuperAuthorityRepository;
import com.caring.manager_service.domain.manager.entity.Manager;
import lombok.RequiredArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@Adaptor
@RequiredArgsConstructor
public class AuthorityAdaptorImpl implements AuthorityAdaptor{

    private final SuperAuthorityRepository superAuthorityRepository;
    private final PersonalSuperAuthorityRepository personalSuperAuthorityRepository;

    @Override
    public Set<PersonalSuperAuthority> queryCurrentPersonalSuperAuthority(Manager manager) {
        return personalSuperAuthorityRepository.findByManager(manager)
                .stream().collect(Collectors.toSet());
    }

    @Override
    public SuperAuthority queryBySuperAuth(SuperAuth superAuth) {
        return superAuthorityRepository.findBySuperAuth(superAuth)
                .orElseThrow(() -> new RuntimeException("not found super authority"));
    }
}
