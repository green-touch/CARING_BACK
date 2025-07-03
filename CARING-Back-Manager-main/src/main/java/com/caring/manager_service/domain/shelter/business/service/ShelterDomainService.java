package com.caring.manager_service.domain.shelter.business.service;

import com.caring.manager_service.domain.manager.entity.Manager;
import com.caring.manager_service.domain.shelter.entity.Shelter;

public interface ShelterDomainService {

    Shelter registerShelter(String name, String location);

    void addShelterStaff(String shelterUuid, Manager manager);

    void addShelterGroup(String shelterUuid, String userUuid);
}
