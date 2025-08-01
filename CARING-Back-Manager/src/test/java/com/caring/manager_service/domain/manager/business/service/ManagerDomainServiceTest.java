package com.caring.manager_service.domain.manager.business.service;

import com.caring.manager_service.common.service.DatabaseCleanUp;
import com.caring.manager_service.domain.manager.entity.Manager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
class ManagerDomainServiceTest {
    //feign을 통해 shelter_uuid 가져왔을 때 서비스 테스트
    private static final String TEST_SHELTER_UUID = "test-shelter-uuid";

    @Autowired
    ManagerDomainService managerDomainService;

    @Autowired
    DatabaseCleanUp databaseCleanUp;

    @BeforeEach
    void setup() {
//        authorityDataInitializer.initAuthorityData();
    }

    @AfterEach
    void cleanUp() {
        databaseCleanUp.truncateAllEntity();
    }

    @Test
    @Transactional
    @DisplayName("이름, 비밀번호, 권한을 통해서 슈퍼 매니저를 등록합니다.")
    void registerSuperManager() {
        // given
        String name = "test_super_manager";
        String password = "test_super_password";
        // when
        Manager findManager = managerDomainService.registerManager(name, password);
        // then
        assertThat(findManager.getName()).isEqualTo(name);
        log.info("findManager.memberCode = {}", findManager.getMemberCode());
    }

}
