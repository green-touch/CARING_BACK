package com.caring.manager_service.presentation.security.service.manager;

import com.caring.manager_service.common.service.RedisService;
import com.caring.manager_service.domain.manager.business.adaptor.ManagerAdaptor;
import com.caring.manager_service.domain.manager.entity.Manager;
import com.caring.manager_service.presentation.manager.service.ManagerLoginUseCase;
import com.caring.manager_service.presentation.security.vo.JwtToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class ManagerTokenServiceTest {

    private ManagerTokenService managerTokenService;

    @Mock
    private Environment env;
    @Mock
    private RedisService redisService;
    @Mock
    private ManagerAdaptor managerAdaptor;
    @Mock
    private ManagerLoginUseCase managerLoginUseCase;

    private Manager testManager;
    private static final String TEST_SECRET_KEY = "testSecretKey1234567890123456789012345678901234567890";

    @BeforeEach
    void setUp() {
        // 테스트용 시크릿 키 설정
        given(env.getProperty("token.secret-manager")).willReturn(TEST_SECRET_KEY);

        // 서비스 초기화
        managerTokenService = new ManagerTokenServiceImpl(env, redisService, managerAdaptor, managerLoginUseCase);

        // 테스트용 매니저 생성
        testManager = Manager.builder()
                .memberCode("CRM#1234567")
                .managerUuid("test-uuid")
                .name("testManager")
                .password("encodedTestPassword")
                .build();
    }

    @Test
    @DisplayName("로그인 시 JWT 토큰이 정상적으로 실행됩니다.")
    void login() {
        // given
        String memberCode = "CRM#1234567";
        String password = "password";
        given(managerLoginUseCase.execute(memberCode, password)).willReturn(testManager);

        // when
        JwtToken token = managerTokenService.login(memberCode, password);

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
                testManager, "", testManager.getAuthorities());
        JwtToken token = managerTokenService.generateToken(authentication);
        String refreshToken = token.getRefreshToken();

        given(managerAdaptor.queryByMemberCode(testManager.getMemberCode())).willReturn(testManager);

        // when
        JwtToken newToken = managerTokenService.reissueToken(refreshToken);

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
        boolean result = managerTokenService.logout(refreshToken);

        // then
        assertThat(result).isTrue();
        verify(redisService).deleteValue(refreshToken);
    }

    @Test
    @DisplayName("리프레시 토큰이 Redis에 존재하는지 확인합니다.")
    void existsRefreshToken() {
        // given
        String refreshToken = "test.refresh.token";
        given(redisService.getValue(refreshToken)).willReturn(testManager.getMemberCode());

        // when
        boolean exists = managerTokenService.existsRefreshToken(refreshToken);

        // then
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Authentication 객체로부터 JWT 토큰이 생성됩니다.")
    void generateToken() {
        // given
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                testManager, "", testManager.getAuthorities());

        // when
        JwtToken token = managerTokenService.generateToken(authentication);

        // then
        assertThat(token.getGrantType()).isEqualTo("Bearer");
        assertThat(token.getAccessToken()).isNotNull();
        assertThat(token.getRefreshToken()).isNotNull();
        verify(redisService).setValue(anyString(), anyString());
    }

}
