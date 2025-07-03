package com.caring.user_service.presentation.user.service;

import com.caring.user_service.common.service.DatabaseCleanUp;
import com.caring.user_service.common.util.RandomNumberUtil;
import com.caring.user_service.domain.user.entity.User;
import com.caring.user_service.domain.user.repository.UserRepository;
import com.caring.user_service.presentation.user.vo.ResponseUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static com.caring.user_service.common.consts.StaticVariable.USER_MEMBER_CODE_PRESET;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@SpringBootTest
class GetUserProfileUseCaseTest {

    @Autowired
    private GetUserProfileUseCase getUserProfileUseCase;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DatabaseCleanUp databaseCleanUp;

    private User testUser;
    private String testUserUuid;

    @BeforeEach
    void setUp() {
        testUserUuid = UUID.randomUUID().toString();
        testUser = User.builder()
                .userUuid(testUserUuid)
                .name("testUser001")
                .password("password")
                .memberCode(RandomNumberUtil.generateRandomMemberCode(USER_MEMBER_CODE_PRESET))
                .build();

        userRepository.save(testUser);
    }

    @AfterEach
    void cleanUp() {
        databaseCleanUp.truncateAllEntity();
    }

    @Test
    @DisplayName("사용자 프로필을 성공적으로 조회한다")
    void getUserProfileSuccess() {
        // when
        ResponseUser result = getUserProfileUseCase.execute(testUserUuid);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getUserUuid()).isEqualTo(testUser.getUserUuid());
        assertThat(result.getName()).isEqualTo(testUser.getName());
        assertThat(result.getMemberCode()).isEqualTo(testUser.getMemberCode());
        assertThat(result.getMemberCode()).startsWith(USER_MEMBER_CODE_PRESET);
    }

    @Test
    @DisplayName("존재하지 않는 사용자 조회 시 404 예외가 발생한다")
    void getUserProfileNotFound() {
        // given
        String nonExistentUuid = UUID.randomUUID().toString();

        // when & then
        assertThatThrownBy(() -> getUserProfileUseCase.execute(nonExistentUuid))
                .isInstanceOf(ResponseStatusException.class)
                .hasFieldOrPropertyWithValue("status", HttpStatus.NOT_FOUND);
    }
}
