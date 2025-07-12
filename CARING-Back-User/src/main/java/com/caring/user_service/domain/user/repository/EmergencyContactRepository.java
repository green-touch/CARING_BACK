package com.caring.user_service.domain.user.repository;

import com.caring.user_service.domain.user.entity.EmergencyContact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmergencyContactRepository extends JpaRepository<EmergencyContact, Long> {
}
