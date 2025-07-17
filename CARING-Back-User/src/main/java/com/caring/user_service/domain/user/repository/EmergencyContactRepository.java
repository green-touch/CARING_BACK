package com.caring.user_service.domain.user.repository;

import com.caring.user_service.domain.user.entity.EmergencyContact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmergencyContactRepository extends JpaRepository<EmergencyContact, Long> {

    Optional<EmergencyContact> findByContactUuid(String contactUuid);
}
