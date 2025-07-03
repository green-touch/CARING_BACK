package com.caring.manager_service.presentation.manager.service;

import com.caring.manager_service.common.AuthorityDataInitializer;
import com.caring.manager_service.common.service.DatabaseCleanUp;
import com.caring.manager_service.domain.manager.entity.Submission;
import com.caring.manager_service.domain.manager.repository.SubmissionRepository;
import com.caring.manager_service.presentation.manager.vo.request.RequestManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
class ApplyManagerUseCaseTest {
    // feign을 통해 shelter_uuid 가져왔을 때 서비스 테스트
    private static final String TEST_SHELTER_UUID = "test-shelter-uuid";

    @Autowired
    ApplyManagerUseCase applyManagerUseCase;
    @Autowired
    SubmissionRepository submissionRepository;
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
    @Transactional
    @DisplayName("일반 매니저를 서버에 처음 등록할 때 필수 데이터를 submission이라는 임시 데이터에 저장합니다." +
            "이렇게 저장된 submission은 permit이 되었을 때 저장할 수 있도록 합니다.")
    void setApplyManagerUseCase() {
        // given
        RequestManager request = RequestManager.builder()
                .name("name")
                .password("password")
                .build();
        // when
        Long submissionId = applyManagerUseCase.execute(request, TEST_SHELTER_UUID);
        // then
        Optional<Submission> findSubmission = submissionRepository.findById(submissionId);
        assertThat(findSubmission).isPresent();
        assertThat(findSubmission.get().getShelterUuid()).isEqualTo(TEST_SHELTER_UUID);
    }

}
