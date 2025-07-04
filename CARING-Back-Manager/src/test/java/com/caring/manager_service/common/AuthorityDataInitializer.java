package com.caring.manager_service.common;

import com.caring.manager_service.domain.authority.entity.Authority;
import com.caring.manager_service.domain.authority.entity.ManagerRole;
import com.caring.manager_service.domain.authority.repository.AuthorityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("test")
public class AuthorityDataInitializer {
    private final AuthorityRepository authorityRepository;

    public void initAuthorityData() {
        if (authorityRepository.count() == 0) {
            authorityRepository.save(Authority
                    .builder()
                    .managerRole(ManagerRole.MANAGE)
                    .build());
            authorityRepository.save(Authority
                    .builder()
                    .managerRole(ManagerRole.SUPER)
                    .build());
            authorityRepository.save(Authority
                    .builder()
                    .managerRole(ManagerRole.ADMIN)
                    .build());
        }
    }
}
