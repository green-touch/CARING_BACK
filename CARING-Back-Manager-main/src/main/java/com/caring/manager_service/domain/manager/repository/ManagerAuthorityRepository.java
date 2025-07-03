package com.caring.manager_service.domain.manager.repository;

import com.caring.manager_service.domain.authority.entity.ManagerAuthority;
import com.caring.manager_service.domain.manager.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ManagerAuthorityRepository extends JpaRepository<ManagerAuthority, Long> {
    List<ManagerAuthority> findByManager(Manager manager);
}
