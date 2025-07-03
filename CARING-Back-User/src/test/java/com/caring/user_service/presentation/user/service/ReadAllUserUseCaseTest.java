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

import java.util.List;
import java.util.UUID;

import static com.caring.user_service.common.consts.StaticVariable.USER_MEMBER_CODE_PRESET;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class ReadAllUserUseCaseTest {

    @Autowired
    private ReadAllUserUseCase readAllUserUseCase;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DatabaseCleanUp databaseCleanUp;

    @BeforeEach
    void setUp() {
        User user1 = User.builder()
                .name("testUser001")
                .password("password1")
                .userUuid(UUID.randomUUID().toString())
                .memberCode(RandomNumberUtil.generateRandomMemberCode(USER_MEMBER_CODE_PRESET))
                .build();

        User user2 = User.builder()
                .name("testUser002")
                .password("password2")
                .userUuid(UUID.randomUUID().toString())
                .memberCode(RandomNumberUtil.generateRandomMemberCode(USER_MEMBER_CODE_PRESET))
                .build();

        User user3 = User.builder()
                .name("testUser003")
                .password("password3")
                .userUuid(UUID.randomUUID().toString())
                .memberCode(RandomNumberUtil.generateRandomMemberCode(USER_MEMBER_CODE_PRESET))
                .build();

        userRepository.saveAll(List.of(user1, user2, user3));
    }

    @AfterEach
    void cleanUp() {
        databaseCleanUp.truncateAllEntity();
    }

    @Test
    @DisplayName("모든 사용자 정보를 성공적으로 조회한다")
    void shouldReadAllUsersSuccessfully() {
        // when
        List<ResponseUser> result = readAllUserUseCase.execute();

        // then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(3);

        System.out.println("조회 결과: " + result);
        // 각 사용자의 정보가 올바르게 매핑되었는지 확인
        result.forEach(responseUser -> {
            assertThat(responseUser.getUserUuid()).isNotNull();
            assertThat(responseUser.getMemberCode()).isNotNull();
            assertThat(responseUser.getName()).isNotNull();
        });

        // 특정 사용자의 정보가 포함되어 있는지 확인
        assertThat(result).anyMatch(user -> user.getName().equals("testUser001"));
        assertThat(result).anyMatch(user -> user.getName().equals("testUser002"));
        assertThat(result).anyMatch(user -> user.getName().equals("testUser003"));
    }

    @Test
    @DisplayName("사용자가 없을 경우 빈 리스트를 반환한다")
    void shouldReturnEmptyListWhenNoUsers() {
        // given
        databaseCleanUp.truncateAllEntity();

        // when
        List<ResponseUser> result = readAllUserUseCase.execute();

        // then
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
    }
}
