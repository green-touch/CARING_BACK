package com.caring.manager_service.presentation.manager.service;

import com.caring.manager_service.common.AuthorityDataInitializer;
import com.caring.manager_service.common.service.DatabaseCleanUp;
import com.caring.manager_service.presentation.manager.vo.request.RequestManager;
import com.caring.manager_service.presentation.manager.vo.response.ResponseSubmission;
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
class GetPendingSubmissionsUseCaseTest {

    private static final String TEST_SHELTER_UUID = "test-shelter-uuid";

    @Autowired
    GetPendingSubmissionsUseCase getPendingSubmissionsUseCase;
    @Autowired
    ApplyManagerUseCase applyManagerUseCase;
    @Autowired
    DatabaseCleanUp databaseCleanUp;
    @Autowired
    AuthorityDataInitializer authorityDataInitializer;

    RequestManager request1;
    RequestManager request2;
    RequestManager request3;

    @BeforeEach
    void setup() {
        authorityDataInitializer.initAuthorityData();
        request1 = RequestManager.builder().name("name1").password("password1").build();
        request2 = RequestManager.builder().name("name2").password("password2").build();
        request3 = RequestManager.builder().name("name3").password("password3").build();
    }

    @AfterEach
    void cleanUp() {
        databaseCleanUp.truncateAllEntity();
    }

    @Test
    @DisplayName("아직 허가되지 않은 매니저 신청들을 조회합니다.")
    void shouldReturnPendingManagerSubmissions() {
        // given
        applyManagerUseCase.execute(request1, TEST_SHELTER_UUID);
        applyManagerUseCase.execute(request2, TEST_SHELTER_UUID);
        applyManagerUseCase.execute(request3, TEST_SHELTER_UUID);

        // when
        List<ResponseSubmission> result = getPendingSubmissionsUseCase.execute();

        // then
        assertThat(result).hasSize(3);
        assertThat(result)
                .extracting(ResponseSubmission::getShelterUuid)
                .containsOnly(TEST_SHELTER_UUID);
    }

}
