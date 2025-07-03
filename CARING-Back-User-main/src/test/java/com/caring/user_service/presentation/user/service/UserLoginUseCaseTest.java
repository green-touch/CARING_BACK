package com.caring.user_service.presentation.user.service;

import com.caring.user_service.common.service.DatabaseCleanUp;
import com.caring.user_service.common.util.RandomNumberUtil;
import com.caring.user_service.domain.user.entity.User;
import com.caring.user_service.domain.user.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;

import static com.caring.user_service.common.consts.StaticVariable.USER_MEMBER_CODE_PRESET;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@SpringBootTest
class UserLoginUseCaseTest {

    @Autowired
    private UserLoginUseCase userLoginUseCase;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DatabaseCleanUp databaseCleanUp;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String testMemberCode;
    private String testWrongMemberCode;
    private String testPassword;

    @BeforeEach
    void setUp() {
        testMemberCode = RandomNumberUtil.generateRandomMemberCode(USER_MEMBER_CODE_PRESET);
        testWrongMemberCode = RandomNumberUtil.generateRandomMemberCode(USER_MEMBER_CODE_PRESET);
        testPassword = "testPassword123";
        String encodedPassword = passwordEncoder.encode(testPassword);

        User testUser = User.builder()
                .name("testUser001")
                .password(encodedPassword)
                .memberCode(testMemberCode)
                .build();

        userRepository.save(testUser);
    }

    @AfterEach
    void cleanUp() {
        databaseCleanUp.truncateAllEntity();
    }

    @Test
    @DisplayName("올바른 회원코드와 비밀번호로 로그인에 성공한다")
    void shouldLoginSuccessfullyWithValidCredentials() {
        // when
        User loggedInUser = userLoginUseCase.execute(testMemberCode, testPassword);

        // then
        assertThat(loggedInUser).isNotNull();
        assertThat(loggedInUser.getMemberCode()).isEqualTo(testMemberCode);
        assertThat(loggedInUser.getName()).isEqualTo("testUser001");
    }

    @Test
    @DisplayName("잘못된 비밀번호로 로그인 시 예외가 발생한다")
    void shouldThrowExceptionWithInvalidPassword() {
        // when & then
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> userLoginUseCase.execute(testMemberCode, "wrongPassword"));

        assertThat(ex.getMessage()).contains("not match password");
    }

    @Test
    @DisplayName("존재하지 않는 회원코드로 로그인 시 404 예외가 발생한다")
    void shouldThrowExceptionWithNonExistentMemberCode() {
        // when & then
        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> userLoginUseCase.execute(testWrongMemberCode, testPassword));

        assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(ex.getReason()).contains("User not found with member code: " + testWrongMemberCode);
    }

    @Test
    @DisplayName("빈 회원코드로 로그인 시 400 Bad Request 예외가 발생한다")
    void shouldThrowNotFoundWithEmptyMemberCode() {
        // when & then
        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> userLoginUseCase.execute("", testPassword));

        assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(ex.getReason()).contains("MemberCode must not be null or blank");
    }


    @Test
    @DisplayName("빈 비밀번호로 로그인 시 예외가 발생한다")
    void shouldThrowExceptionWithEmptyPassword() {
        // when & then
        assertThrows(
                IllegalArgumentException.class,
                () -> userLoginUseCase.execute(testMemberCode, ""));
    }
}
