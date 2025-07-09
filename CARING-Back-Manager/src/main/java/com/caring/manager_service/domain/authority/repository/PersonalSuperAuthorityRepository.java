package com.caring.manager_service.domain.authority.repository;

import com.caring.manager_service.domain.authority.entity.PersonalSuperAuthority;
import com.caring.manager_service.domain.authority.entity.SuperAuth;
import com.caring.manager_service.domain.manager.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonalSuperAuthorityRepository extends JpaRepository<PersonalSuperAuthority, Long> {
    List<PersonalSuperAuthority> findByManager(Manager manager);

    @Modifying // 이 어노테이션은 UPDATE/DELETE 쿼리에서 필수입니다.
    @Query("DELETE FROM PersonalSuperAuthority psa WHERE psa.superAuthority.superAuth IN :superAuths")
    void deleteBySuperAuthIn(@Param("superAuths") List<SuperAuth> superAuths);}
