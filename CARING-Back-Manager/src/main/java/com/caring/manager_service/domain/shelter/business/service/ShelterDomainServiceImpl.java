package com.caring.manager_service.domain.shelter.business.service;

import com.caring.manager_service.common.annotation.DomainService;
import com.caring.manager_service.domain.manager.entity.Manager;
import com.caring.manager_service.domain.shelter.entity.Shelter;
import com.caring.manager_service.domain.shelter.entity.ShelterUser;
import com.caring.manager_service.domain.shelter.repository.ShelterRepository;
import com.caring.manager_service.domain.shelter.repository.ShelterUserRepository;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@DomainService
@RequiredArgsConstructor
public class ShelterDomainServiceImpl implements ShelterDomainService {

    private final ShelterRepository shelterRepository;
    private final ShelterUserRepository shelterUserRepository;

    @Override
    public Shelter registerShelter(String name, String location) {
        Shelter newShelter = Shelter.builder()
                .name(name)
                .location(location)
                .shelterUuid(UUID.randomUUID().toString())
                .build();
        return shelterRepository.save(newShelter);
    }

    @Override
    public void addShelterStaff(String shelterUuid, Manager manager) {
        manager.groupedInShelter(shelterUuid);
    }

    @Override
    public void addShelterGroup(String shelterUuid, String userUuid) {
        if (!shelterUserRepository.existsByShelterUuidAndUserUuid(shelterUuid, userUuid)) {
            ShelterUser shelterUser = ShelterUser.builder()
                    .shelterUuid(shelterUuid)
                    .userUuid(userUuid)
                    .build();
            shelterUserRepository.save(shelterUser);
        }
    }

}
