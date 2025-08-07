package com.caring.shelter_service.domain.shelter.repository;

import com.caring.shelter_service.domain.shelter.entity.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShelterRepository extends JpaRepository<Shelter, Long> {
}
