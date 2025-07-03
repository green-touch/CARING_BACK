package com.caring.manager_service.presentation.manager.service;

import com.caring.manager_service.common.AuthorityDataInitializer;
import com.caring.manager_service.common.service.DatabaseCleanUp;
import com.caring.manager_service.domain.authority.business.adaptor.AuthorityAdaptor;
import com.caring.manager_service.domain.authority.entity.Authority;
import com.caring.manager_service.domain.authority.entity.ManagerRole;
import com.caring.manager_service.domain.manager.business.service.ManagerDomainService;
import com.caring.manager_service.presentation.manager.vo.response.ResponseSpecificManager;
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
class GetAllManagersUseCaseTest {
    @Autowired
    GetAllManagersUseCase getAllManagersUseCase;
    @Autowired
    AuthorityDataInitializer authorityDataInitializer;
    @Autowired
    ManagerDomainService managerDomainService;
    @Autowired
    AuthorityAdaptor authorityAdaptor;
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
    @DisplayName("모든 매니저의 정보를 response 형태로 불러옵니다.")
    void getAllManagersUseCase() {
        // given
        Authority managerAuth = authorityAdaptor.queryByManagerRole(ManagerRole.MANAGE);
        Authority superAuth = authorityAdaptor.queryByManagerRole(ManagerRole.SUPER);
        managerDomainService.registerManager("name1", "password", managerAuth);
        managerDomainService.registerManager("name2", "password", superAuth);
        managerDomainService.registerManager("name3", "password", superAuth);
        // when
        List<ResponseSpecificManager> result = getAllManagersUseCase.execute();
        // then
        assertThat(result.size()).isEqualTo(3);
        assertThat(result.get(2).getResponseAuthority().getRoles()).contains(ManagerRole.SUPER.getKey());
    }

    @Test
    @DisplayName("매니저가 없을 경우 빈 리스트를 반환합니다.")
    void getAllManagersUseCase_shouldReturnEmptyList_whenNoManagersExist() {
        // given
        // 초기 authority는 있지만 매니저 등록 안 함

        // when
        List<ResponseSpecificManager> result = getAllManagersUseCase.execute();

        // then
        assertThat(result).isEmpty();
    }

}
