package com.caring.manager_service.domain.shelter.repository;

import com.caring.manager_service.domain.shelter.entity.ShelterUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShelterUserRepository extends JpaRepository<ShelterUser, Long> {
    boolean existsByShelterUuidAndUserUuid(String shelterUuid, String userUuid);
}
