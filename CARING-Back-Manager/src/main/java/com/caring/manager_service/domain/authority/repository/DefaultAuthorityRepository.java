package com.caring.manager_service.domain.authority.repository;

import com.caring.manager_service.domain.authority.entity.DefaultAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DefaultAuthorityRepository extends JpaRepository<DefaultAuthority, Long> {
}
