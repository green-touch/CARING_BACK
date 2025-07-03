package com.caring.manager_service.domain.manager.business.adaptor;

import com.caring.manager_service.common.service.DatabaseCleanUp;
import com.caring.manager_service.domain.manager.entity.Manager;
import com.caring.manager_service.domain.manager.entity.Submission;
import com.caring.manager_service.domain.manager.entity.SubmissionStatus;
import com.caring.manager_service.domain.manager.repository.ManagerRepository;
import com.caring.manager_service.domain.manager.repository.SubmissionRepository;
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
    @Autowired
    private SubmissionRepository submissionRepository;
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

    @Test
    @Transactional
    @DisplayName("신청의 uuid를 통해 Submission entity를 불러옵니다.")
    void querySubmissionByUuid() {
        // given
        Submission submission = Submission.builder()
                .submissionUuid(UUID.randomUUID().toString())
                .name("name")
                .password("password")
                .status(SubmissionStatus.APPLY)
                .shelterUuid(TEST_SHELTER_UUID)
                .build();
        submissionRepository.save(submission);

        // when
        Submission findSubmission = managerAdaptor.querySubmissionByUuid(submission.getSubmissionUuid());

        // then
        assertThat(findSubmission)
                .isNotNull()
                .satisfies(sub -> {
                    assertThat(sub.getShelterUuid()).isEqualTo(TEST_SHELTER_UUID);
                    assertThat(sub.getStatus()).isEqualTo(SubmissionStatus.APPLY);
                });
    }

    @Test
    @Transactional
    @DisplayName("SubmissionStatus(APPLY, PERMIT, REJECTED)를 통해 Submission 리스트를 불러옵니다.")
    void querySubmissionsByStatus() {
        // given
        List<Submission> submissions = List.of(
                Submission.builder()
                        .submissionUuid(UUID.randomUUID().toString())
                        .name("name1")
                        .password("password1")
                        .status(SubmissionStatus.APPLY)
                        .shelterUuid(TEST_SHELTER_UUID)
                        .build(),
                Submission.builder()
                        .submissionUuid(UUID.randomUUID().toString())
                        .name("name2")
                        .password("password2")
                        .status(SubmissionStatus.APPLY)
                        .shelterUuid(TEST_SHELTER_UUID)
                        .build(),
                Submission.builder()
                        .submissionUuid(UUID.randomUUID().toString())
                        .name("name3")
                        .password("password3")
                        .status(SubmissionStatus.REJECTED)
                        .shelterUuid(TEST_SHELTER_UUID)
                        .build(),
                Submission.builder()
                        .submissionUuid(UUID.randomUUID().toString())
                        .name("name3")
                        .password("password3")
                        .status(SubmissionStatus.PERMIT)
                        .shelterUuid(TEST_SHELTER_UUID)
                        .build());
        submissionRepository.saveAll(submissions);

        // when
        List<Submission> appliedSubmissions = managerAdaptor.querySubmissionsByStatus(SubmissionStatus.APPLY);
        List<Submission> rejectedSubmissions = managerAdaptor.querySubmissionsByStatus(SubmissionStatus.REJECTED);
        List<Submission> permittedSubmissions = managerAdaptor.querySubmissionsByStatus(SubmissionStatus.PERMIT);

        // then
        assertThat(appliedSubmissions).isNotNull().hasSize(2);
        assertThat(rejectedSubmissions).isNotNull().hasSize(1);
        assertThat(permittedSubmissions).isNotNull().hasSize(1);
    }

}
