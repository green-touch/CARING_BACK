package com.caring.manager_service.common.service;

import com.caring.manager_service.domain.authority.converter.SuperAuthConverter;
import com.caring.manager_service.domain.authority.entity.SuperAuth;
import com.caring.manager_service.domain.authority.entity.SuperAuthority;
import com.caring.manager_service.domain.authority.repository.SuperAuthorityRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Component
@Profile("!test")
@RequiredArgsConstructor
public class AuthorityDataInitializer {

    private final SuperAuthorityRepository superAuthorityRepository;

    @PostConstruct
    public void setupAuthority() {
        setSuperAuthority();
    }

    private void setSuperAuthority() {
        List<SuperAuth> existingAuthorities = superAuthorityRepository.findAll().stream()
                .map(SuperAuthority::getSuperAuth)
                .collect(Collectors.toList());

        List<SuperAuth> allSuperAuths = Arrays.asList(SuperAuth.values());

        List<SuperAuth> newAuthoritiesToAdd = allSuperAuths.stream()
                .filter(superAuth -> !existingAuthorities.contains(superAuth))
                .collect(Collectors.toList());
        if (!newAuthoritiesToAdd.isEmpty()) {
            List<SuperAuthority> collect = newAuthoritiesToAdd.stream()
                    .map(SuperAuthConverter::toSuperAuthority)
                    .collect(Collectors.toList());
            superAuthorityRepository.saveAll(collect);
        }
    }
}
