package com.caring.user_service.presentation.user.service;

import com.caring.user_service.common.service.DatabaseCleanUp;
import com.caring.user_service.common.util.RandomNumberUtil;
import com.caring.user_service.domain.user.entity.User;
import com.caring.user_service.domain.user.repository.UserRepository;
import com.caring.user_service.presentation.user.vo.ResponseUserShelterUuid;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static com.caring.user_service.common.consts.StaticVariable.USER_MEMBER_CODE_PRESET;
import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class GetUserShelterUuidUseCaseTest {

    @Autowired
    private GetUserShelterUuidUseCase getUserShelterUuidUseCase;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DatabaseCleanUp databaseCleanUp;

    private String testUserUuid;
    private String testShelterUuid;

    @BeforeEach
    void setUp() {
        testUserUuid = UUID.randomUUID().toString();
        testShelterUuid = UUID.randomUUID().toString();

        User testUser = User.builder()
                .userUuid(testUserUuid)
                .name("testUser001")
                .password("password")
                .memberCode(RandomNumberUtil.generateRandomMemberCode(USER_MEMBER_CODE_PRESET))
                .shelterUuid(testShelterUuid)
                .build();

        userRepository.save(testUser);
    }

    @AfterEach
    void cleanUp() {
        databaseCleanUp.truncateAllEntity();
    }

    @Test
    @DisplayName("사용자의 보호소 UUID를 성공적으로 조회한다")
    void shouldGetUserShelterUuidSuccessfully() {
        // when
        ResponseUserShelterUuid result = getUserShelterUuidUseCase.execute(testUserUuid);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getShelterUuid()).isEqualTo(testShelterUuid);
    }

    @Test
    @DisplayName("존재하지 않는 사용자 UUID로 조회 시 404 예외를 발생시킨다")
    void shouldThrowNotFoundWhenUserDoesNotExist() {
        // given
        String nonExistentUserUuid = "non-existent-uuid";

        // when & then
        assertThatThrownBy(() -> getUserShelterUuidUseCase.execute(nonExistentUserUuid))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("404 NOT_FOUND");
    }

    @Test
    @DisplayName("보호소 UUID가 없는 사용자 조회 시 null을 반환한다")
    void shouldReturnNullForUserWithoutShelter() {
        // given
        User userWithoutShelter = User.builder()
                .userUuid(UUID.randomUUID().toString())
                .name("UserWithoutShelter")
                .password("password")
                .memberCode(RandomNumberUtil.generateRandomMemberCode(USER_MEMBER_CODE_PRESET))
                .shelterUuid(null)
                .build();
        userRepository.save(userWithoutShelter);

        // when
        ResponseUserShelterUuid result = getUserShelterUuidUseCase.execute(userWithoutShelter.getUserUuid());

        // then
        assertThat(result).isNotNull();
        assertThat(result.getShelterUuid()).isNull();
    }
}
