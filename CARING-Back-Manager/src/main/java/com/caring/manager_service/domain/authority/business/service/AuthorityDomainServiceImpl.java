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

    private final SuperAuthorityRepository superAuthorityRepository;
    private final PersonalSuperAuthorityRepository personalSuperAuthorityRepository;
    @Override
    public Long switchSuperAuthority(Manager manager, List<SuperAuth> activeSuperAuths) {
        Set<SuperAuth> currentSuperAuthority =
                personalSuperAuthorityRepository.findByManager(manager).stream()
                        .map(personalSuperAuthority -> personalSuperAuthority.getSuperAuthority().getSuperAuth())
                        .collect(Collectors.toSet());

        Set<SuperAuth> activeSuperAuthSet = new HashSet<>(activeSuperAuths);

        // BLACK LIST
        List<SuperAuth> blackList = currentSuperAuthority.stream()
                .filter(csa -> !activeSuperAuthSet.contains(csa))
                .collect(Collectors.toList());

        // WHITE LIST
        List<SuperAuthority> whiteList = activeSuperAuthSet.stream()
                .filter(asa -> !currentSuperAuthority.contains(asa))
                .map(asa -> superAuthorityRepository.findBySuperAuth(asa)
                        .orElseThrow(() -> new RuntimeException("not found super auth")))
                .collect(Collectors.toList());

        List<PersonalSuperAuthority> save = new ArrayList<>();
        for (SuperAuthority superAuthority : whiteList) {
            save.add(
                    PersonalSuperAuthority.builder()
                            .superAuthority(superAuthority)
                            .manager(manager)
                            .build()
            );
        }

        personalSuperAuthorityRepository.deleteBySuperAuthIn(blackList);
        personalSuperAuthorityRepository.saveAll(save);

        return manager.getId();
    }
}
