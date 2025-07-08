package com.caring.manager_service.domain.authority.repository;

import com.caring.manager_service.domain.authority.entity.PersonalSuperAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalSuperAuthorityRepository extends JpaRepository<PersonalSuperAuthority, Long> {
}
