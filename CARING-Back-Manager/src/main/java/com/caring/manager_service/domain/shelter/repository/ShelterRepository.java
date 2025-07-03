package com.caring.manager_service.domain.shelter.repository;

import com.caring.manager_service.domain.shelter.entity.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShelterRepository extends JpaRepository<Shelter, Long> {
    Optional<Shelter> findByShelterUuid(String shelterUuid);

    boolean existsByShelterUuid(String shelterUuid);
}
