package com.caring.user_service.presentation.user.service;

import com.caring.user_service.common.service.DatabaseCleanUp;
import com.caring.user_service.domain.user.entity.User;
import com.caring.user_service.domain.user.repository.UserRepository;
import com.caring.user_service.presentation.user.vo.RequestUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class RegisterUserUseCaseTest {

    @Autowired
    private RegisterUserUseCase registerUserUseCase;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DatabaseCleanUp databaseCleanUp;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private RequestUser requestUser;

    @BeforeEach
    void setUp() {
        requestUser = new RequestUser("testUser001", "password123");
    }

    @AfterEach
    void cleanUp() {
        databaseCleanUp.truncateAllEntity();
    }

    @Test
    @DisplayName("사용자를 성공적으로 등록한다")
    void shouldRegisterUserSuccessfully() {
        // when
        Long userId = registerUserUseCase.execute(requestUser);

        // then
        assertThat(userId).isNotNull();

        User savedUser = userRepository.findById(userId).orElse(null);
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getName()).isEqualTo(requestUser.getName());
        // 비밀번호는 매칭 방식으로 검증
        assertThat(passwordEncoder.matches(requestUser.getPassword(), savedUser.getPassword())).isTrue();
        assertThat(savedUser.getUserUuid()).isNotNull();
        assertThat(savedUser.getMemberCode()).isNotNull();
    }

    @Test
    @DisplayName("동일한 이름의 사용자를 등록할 수 있다")
    void shouldRegisterUserWithSameName() {
        // given
        registerUserUseCase.execute(requestUser);

        // when
        Long secondUserId = registerUserUseCase.execute(requestUser);

        // then
        assertThat(secondUserId).isNotNull();

        User secondUser = userRepository.findById(secondUserId).orElse(null);
        assertThat(secondUser).isNotNull();
        assertThat(secondUser.getName()).isEqualTo(requestUser.getName());
        assertThat(secondUser.getUserUuid()).isNotNull();
        assertThat(secondUser.getMemberCode()).isNotNull();
    }

    @Test
    @DisplayName("빈 이름으로 사용자 등록 시 예외가 발생한다")
    void shouldThrowExceptionWhenNameIsEmpty() {
        // given
        RequestUser emptyNameUser = new RequestUser("", "password123");

        // when & then
        org.junit.jupiter.api.Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> registerUserUseCase.execute(emptyNameUser));
    }

    @Test
    @DisplayName("빈 비밀번호로 사용자 등록 시 예외가 발생한다")
    void shouldThrowExceptionWhenPasswordIsEmpty() {
        // given
        RequestUser emptyPasswordUser = new RequestUser("testUser001", "");

        // when & then
        org.junit.jupiter.api.Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> registerUserUseCase.execute(emptyPasswordUser));
    }
}
