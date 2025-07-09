package com.caring.manager_service.domain.authority.repository;

import com.caring.manager_service.domain.authority.entity.SuperAuth;
import com.caring.manager_service.domain.authority.entity.SuperAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SuperAuthorityRepository extends JpaRepository<SuperAuthority, Long> {
    Optional<SuperAuthority> findBySuperAuth(SuperAuth superAuth);
}
