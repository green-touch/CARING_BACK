package com.caring.manager_service.domain.authority.repository;

import com.caring.manager_service.domain.authority.entity.SuperAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuperAuthorityRepository extends JpaRepository<SuperAuthority, Long> {
}
