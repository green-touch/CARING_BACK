package com.caring.manager_service.common.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
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

    private final AuthorityRepository authorityRepository;

    @PostConstruct
    public void setup() {
        Set<ManagerRole> allInDatabase = authorityRepository.findAllManagerRoles();
        List<Authority> notInserted = Arrays.stream(ManagerRole.values())
                .filter(managerRole -> !allInDatabase.contains(managerRole))
                .map(managerRole ->
                        Authority.builder()
                                .managerRole(managerRole)
                                .build())
                .collect(Collectors.toList());
        authorityRepository.saveAll(notInserted);

    }
}
