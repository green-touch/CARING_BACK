package com.caring.manager_service.domain.manager.business.adaptor;

import com.caring.manager_service.common.service.DatabaseCleanUp;
import com.caring.manager_service.domain.manager.entity.Manager;
import com.caring.manager_service.domain.manager.repository.ManagerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
class ManagerAdaptorTest {

    private static final String TEST_SHELTER_UUID = "test-shelter-uuid";
    private static final String TEST_MANAGER1_NAME = "manager1";
    private static final String TEST_MANAGER2_NAME = "manager2";

    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private ManagerAdaptor managerAdaptor;
//    @Autowired
//    private SubmissionRepository submissionRepository;
    @Autowired
    private DatabaseCleanUp databaseCleanUp;

    private Manager manager1;
    private Manager manager2;

    @BeforeEach
    void setup() {
        manager1 = Manager.builder()
                .name(TEST_MANAGER1_NAME)
                .managerUuid(UUID.randomUUID().toString())
                .password("password1")
                .memberCode(TEST_MANAGER1_NAME)
                .shelterUuid(TEST_SHELTER_UUID)
                .build();

        manager2 = Manager.builder()
                .name(TEST_MANAGER2_NAME)
                .managerUuid(UUID.randomUUID().toString())
                .password("password2")
                .memberCode(TEST_MANAGER2_NAME)
                .shelterUuid(TEST_SHELTER_UUID)
                .build();

        managerRepository.saveAll(List.of(manager1, manager2));
    }

    @AfterEach
    void cleanUp() {
        databaseCleanUp.truncateAllEntity();
    }

    @Test
    @DisplayName("MemberCode를 통해 Manager entity를 가져옵니다.")
    void queryByMemberCode() {
        // when
        Manager findManager = managerAdaptor.queryByMemberCode(TEST_MANAGER1_NAME);

        // then
        assertThat(findManager)
                .isNotNull()
                .satisfies(manager -> {
                    assertThat(manager.getName()).isEqualTo(TEST_MANAGER1_NAME);
                    assertThat(manager.getMemberCode()).isEqualTo(TEST_MANAGER1_NAME);
                });
    }

    @Test
    @DisplayName("Manager uuid를 통해 Manager entity를 가져옵니다.")
    void queryByManagerUuid() {
        // when
        Manager findManager = managerAdaptor.queryByManagerUuid(manager1.getManagerUuid());

        // then
        assertThat(findManager)
                .isNotNull()
                .satisfies(manager -> {
                    assertThat(manager.getMemberCode()).isEqualTo(TEST_MANAGER1_NAME);
                    assertThat(manager.getName()).isEqualTo(TEST_MANAGER1_NAME);
                });
    }

    @Test
    @Transactional
    @DisplayName("보호소에 소속된 모든 manager들을 불러옵니다.")
    void queryByShelter() {
        // when
        List<Manager> managers = managerAdaptor.queryByShelter(TEST_SHELTER_UUID);

        // then
        assertThat(managers)
                .isNotNull()
                .hasSize(2)
                .satisfies(managerList -> {
                    assertThat(managerList.get(0).getMemberCode()).isEqualTo(TEST_MANAGER1_NAME);
                    assertThat(managerList.get(1).getMemberCode()).isEqualTo(TEST_MANAGER2_NAME);
                });
    }
}
