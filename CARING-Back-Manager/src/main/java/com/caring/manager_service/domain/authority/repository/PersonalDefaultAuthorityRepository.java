package com.caring.manager_service.domain.authority.repository;

import com.caring.manager_service.domain.authority.entity.PersonalDefaultAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalDefaultAuthorityRepository extends JpaRepository<PersonalDefaultAuthority, Long> {
}
