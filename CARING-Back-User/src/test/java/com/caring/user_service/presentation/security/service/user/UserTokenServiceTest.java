package com.caring.user_service.presentation.security.service.user;

import com.caring.user_service.common.service.RedisService;
import com.caring.user_service.domain.user.business.adaptor.UserAdaptor;
import com.caring.user_service.domain.user.entity.Role;
import com.caring.user_service.domain.user.entity.User;
import com.caring.user_service.presentation.security.vo.JwtToken;
import com.caring.user_service.presentation.user.service.UserLoginUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserTokenServiceTest {

    private UserTokenServiceImpl userTokenService;

    @Mock
    private Environment env;
    @Mock
    private RedisService redisService;
    @Mock
    private UserAdaptor userAdaptor;
    @Mock
    private UserLoginUseCase userLoginUseCase;

    private User testUser;
    private static final String TEST_SECRET_KEY = "testSecretKey1234567890123456789012345678901234567890";

    @BeforeEach
    void setUp() {
        // 테스트용 시크릿 키 설정
        given(env.getProperty("token.secret-user")).willReturn(TEST_SECRET_KEY);

        // 서비스 초기화
        userTokenService = new UserTokenServiceImpl(env, redisService, userAdaptor, userLoginUseCase);

        // 테스트용 유저 생성
        testUser = User.builder()
                .memberCode("CRU#1234567")
                .userUuid("test-uuid")
                .role(Role.USER)
                .name("testUser")
                .password("encodedTestPassword")
                .build();
    }

    @Test
    @DisplayName("로그인 시 JWT 토큰이 정상적으로 생성됩니다.")
    void login() {
        // given
        String memberCode = "CRU#1234567";
        String password = "password";
        given(userLoginUseCase.execute(memberCode, password)).willReturn(testUser);

        // when
        JwtToken token = userTokenService.login(memberCode, password);

        // then
        assertThat(token.getGrantType()).isEqualTo("Bearer");
        assertThat(token.getAccessToken()).isNotNull();
        assertThat(token.getRefreshToken()).isNotNull();
    }

    @Test
    @DisplayName("토큰 재발급 시 완전히 새로운 JWT 토큰이 생성됩니다.")
    void reissueToken() {
        // given
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                testUser, "", testUser.getAuthorities());
        JwtToken token = userTokenService.generateToken(authentication);
        String refreshToken = token.getRefreshToken();

        given(userAdaptor.queryUserByMemberCode(testUser.getMemberCode())).willReturn(testUser);

        // when
        JwtToken newToken = userTokenService.reissueToken(refreshToken);

        // then
        assertThat(newToken.getGrantType()).isEqualTo("Bearer");
        assertThat(newToken.getAccessToken()).isNotNull();
        assertThat(newToken.getRefreshToken()).isNotNull();
        verify(redisService).deleteValue(refreshToken);

        //같은 시각에 새로 발급 하더라도, 토큰의 id가 다르게 설정되었기에 문제없이 테스트 통과됨
        assertThat(newToken.getAccessToken()).isNotEqualTo(token.getAccessToken());
        assertThat(newToken.getRefreshToken()).isNotEqualTo(token.getRefreshToken());
    }

    @Test
    @DisplayName("로그아웃 시 리프레시 토큰이 삭제됩니다.")
    void logout() {
        // given
        String refreshToken = "test.refresh.token";

        // when
        boolean result = userTokenService.logout(refreshToken);

        // then
        assertThat(result).isTrue();
        verify(redisService).deleteValue(refreshToken);
    }

    @Test
    @DisplayName("리프레시 토큰이 Redis에 존재하는지 확인합니다.")
    void existsRefreshToken() {
        // given
        String refreshToken = "test.refresh.token";
        given(redisService.getValue(refreshToken)).willReturn(testUser.getMemberCode());

        // when
        boolean exists = userTokenService.existsRefreshToken(refreshToken);

        // then
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Authentication 객체로부터 JWT 토큰이 생성됩니다.")
    void generateToken() {
        // given
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                testUser, "", testUser.getAuthorities());

        // when
        JwtToken token = userTokenService.generateToken(authentication);

        // then
        assertThat(token.getGrantType()).isEqualTo("Bearer");
        assertThat(token.getAccessToken()).isNotNull();
        assertThat(token.getRefreshToken()).isNotNull();
        verify(redisService).setValue(anyString(), anyString());
    }
}
