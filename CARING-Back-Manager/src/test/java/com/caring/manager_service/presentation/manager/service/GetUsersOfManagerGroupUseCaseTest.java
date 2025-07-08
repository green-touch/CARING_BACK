package com.caring.manager_service.presentation.manager.service;

import com.caring.manager_service.common.AuthorityDataInitializer;
import com.caring.manager_service.common.service.DatabaseCleanUp;
import com.caring.manager_service.domain.manager.business.adaptor.ManagerAdaptor;
import com.caring.manager_service.domain.manager.business.service.ManagerDomainService;
import com.caring.manager_service.domain.manager.entity.Manager;
import com.caring.manager_service.presentation.manager.vo.response.ResponseUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
class GetUsersOfManagerGroupUseCaseTest {
    @Autowired
    GetUsersOfManagerGroupUseCase getUsersOfManagerGroupUseCase;
    @Autowired
    ManagerDomainService managerDomainService;
    @Autowired
    AuthorityAdaptor authorityAdaptor;
    @Autowired
    ManagerAdaptor managerAdaptor;
    @Autowired
    AuthorityDataInitializer authorityDataInitializer;
    @Autowired
    DatabaseCleanUp databaseCleanUp;

    @BeforeEach
    void setup() {
        authorityDataInitializer.initAuthorityData();
    }

    @AfterEach
    void cleanUp() {
        databaseCleanUp.truncateAllEntity();
    }

    @Test
    @DisplayName("매니저 그룹의 유저 목록을 조회합니다.")
    void getUsersOfManagerGroupUseCase() {
        // given
        Authority managerAuth = authorityAdaptor.queryByManagerRole(ManagerRole.MANAGE);
        managerDomainService.registerManager("name1", "password", managerAuth);

        // 등록된 매니저의 memberCode 가져오기
        Manager manager = managerAdaptor.queryAll().get(0);
        String memberCode = manager.getMemberCode();

        // when
        List<ResponseUser> result = getUsersOfManagerGroupUseCase.execute(memberCode);

        // then
        assertThat(result).isNotNull();
    }

}
