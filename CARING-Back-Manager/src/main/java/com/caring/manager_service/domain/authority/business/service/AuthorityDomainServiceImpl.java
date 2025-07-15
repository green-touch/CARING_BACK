package com.caring.manager_service.domain.authority.business.service;

import com.caring.manager_service.common.annotation.DomainService;
import com.caring.manager_service.common.util.EnumConvertUtil;
import com.caring.manager_service.domain.authority.entity.PersonalSuperAuthority;
import com.caring.manager_service.domain.authority.entity.SuperAuth;
import com.caring.manager_service.domain.authority.entity.SuperAuthority;
import com.caring.manager_service.domain.authority.repository.PersonalSuperAuthorityRepository;
import com.caring.manager_service.domain.authority.repository.SuperAuthorityRepository;
import com.caring.manager_service.domain.manager.entity.Manager;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@DomainService
@Transactional
@RequiredArgsConstructor
public class AuthorityDomainServiceImpl implements AuthorityDomainService{

    private final PersonalSuperAuthorityRepository personalSuperAuthorityRepository;

    @Override
    public void saveAllActiveSuperAuthority(Manager manager, List<SuperAuthority> activeSuperAuthorityList) {
        List<PersonalSuperAuthority> savePersonalAuth = new ArrayList<>();
        for (SuperAuthority superAuthority : activeSuperAuthorityList) {
            savePersonalAuth.add(
                    PersonalSuperAuthority.builder()
                            .superAuthority(superAuthority)
                            .manager(manager)
                            .build()
            );
        }
        personalSuperAuthorityRepository.saveAll(savePersonalAuth);
    }

    @Override
    public void removeAllInactiveSuperAuthority(Manager manager, List<SuperAuth> unactiveSuperAuthList) {
        personalSuperAuthorityRepository.deleteBySuperAuthIn(manager, unactiveSuperAuthList);
    }
}
