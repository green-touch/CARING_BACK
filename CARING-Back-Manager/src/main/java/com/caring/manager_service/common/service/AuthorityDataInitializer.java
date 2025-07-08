package com.caring.manager_service.common.service;

import com.caring.manager_service.domain.authority.converter.DefaultAuthConverter;
import com.caring.manager_service.domain.authority.converter.SuperAuthConverter;
import com.caring.manager_service.domain.authority.entity.DefaultAuth;
import com.caring.manager_service.domain.authority.entity.DefaultAuthority;
import com.caring.manager_service.domain.authority.entity.SuperAuth;
import com.caring.manager_service.domain.authority.entity.SuperAuthority;
import com.caring.manager_service.domain.authority.repository.DefaultAuthorityRepository;
import com.caring.manager_service.domain.authority.repository.SuperAuthorityRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Component
@Profile("!test")
@RequiredArgsConstructor
public class AuthorityDataInitializer {

    private final DefaultAuthorityRepository defaultAuthorityRepository;
    private final SuperAuthorityRepository superAuthorityRepository;

    @PostConstruct
    public void setupAuthority() {
        setDefaultAuthority();
        setSuperAuthority();
    }

    private void setDefaultAuthority() {
        List<DefaultAuth> existingAuthorities = defaultAuthorityRepository.findAll().stream()
                .map(DefaultAuthority::getDefaultAuth)
                .collect(Collectors.toList());

        List<DefaultAuth> allDefaultAuths = Arrays.asList(DefaultAuth.values());

        List<DefaultAuth> newAuthoritiesToAdd = allDefaultAuths.stream()
                .filter(auth -> !existingAuthorities.contains(auth))
                .collect(Collectors.toList());

        if (!newAuthoritiesToAdd.isEmpty()) {
            // 새로운 권한 엔티티들을 생성합니다.
            List<DefaultAuthority> authoritiesToSave = newAuthoritiesToAdd.stream()
                    .map(DefaultAuthConverter::toDefaultAuthority) // Converter 사용
                    .collect(Collectors.toList());

            // 데이터베이스에 저장합니다.
            defaultAuthorityRepository.saveAll(authoritiesToSave);
        }

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
