package com.caring.manager_service.domain.manager.repository;

import com.caring.manager_service.domain.manager.entity.Manager;
import com.caring.manager_service.domain.manager.entity.ManagerGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ManagerGroupRepository extends JpaRepository<ManagerGroup, Long> {
    @Query("SELECT mg.userUuid FROM ManagerGroup mg WHERE mg.manager = :manager")
    List<String> findUsersByManager(@Param("manager") Manager manager);

    @Query("SELECT mg.userUuid FROM ManagerGroup mg WHERE mg.manager.memberCode = :memberCode")
    List<String> findUserUuidListByManagerMemberCode(String memberCode);

    boolean existsByManagerMemberCodeAndUserUuid(String memberCode, String userUuid);
    List<String> findAllByManagerMemberCode(String managerCode);
}
