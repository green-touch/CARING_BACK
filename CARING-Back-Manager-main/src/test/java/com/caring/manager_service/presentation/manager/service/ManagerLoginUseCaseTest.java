package com.caring.manager_service.presentation.manager.service;

import com.caring.manager_service.domain.manager.business.adaptor.ManagerAdaptor;
import com.caring.manager_service.domain.manager.business.validator.ManagerValidator;
import com.caring.manager_service.domain.manager.entity.Manager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ManagerLoginUseCaseTest {
    @Mock
    private ManagerAdaptor managerAdaptor;

    @Mock
    private ManagerValidator managerValidator;

    private ManagerLoginUseCase managerLoginUseCase;

    private Manager manager;
    private String memberCode;
    private String password;

    @BeforeEach
    void setup() {
        managerLoginUseCase = new ManagerLoginUseCase(managerAdaptor, managerValidator);

        // 테스트 데이터 설정
        memberCode = "test-member-code";
        password = "test-password";
        manager = Manager.builder()
                .memberCode(memberCode)
                .password(password)
                .build();
    }

    @Test
    @DisplayName("올바른 memberCode와 password로 로그인에 성공한다")
    void loginSuccess() {
        // given
        when(managerAdaptor.queryByMemberCode(memberCode)).thenReturn(manager);
        doNothing().when(managerValidator).checkPasswordEncode(manager, password);

        // when
        Manager result = managerLoginUseCase.execute(memberCode, password);

        // then
        assertThat(result).isEqualTo(manager);
        verify(managerAdaptor).queryByMemberCode(memberCode);
        verify(managerValidator).checkPasswordEncode(manager, password);
    }

    @Test
    @DisplayName("존재하지 않는 memberCode로 로그인 시도 시 예외가 발생한다")
    void loginWithNonExistentMemberCodeThrowsException() {
        // given
        when(managerAdaptor.queryByMemberCode(memberCode))
                .thenThrow(new IllegalArgumentException("Manager not found"));

        // when & then
        assertThatThrownBy(() -> managerLoginUseCase.execute(memberCode, password))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Manager not found");
        verify(managerAdaptor).queryByMemberCode(memberCode);
        verify(managerValidator, never()).checkPasswordEncode(any(), any());
    }

    @Test
    @DisplayName("잘못된 password로 로그인 시도 시 예외가 발생한다")
    void loginWithWrongPasswordThrowsException() {
        // given
        when(managerAdaptor.queryByMemberCode(memberCode)).thenReturn(manager);
        doThrow(new IllegalArgumentException("not match password"))
                .when(managerValidator).checkPasswordEncode(manager, password);

        // when & then
        assertThatThrownBy(() -> managerLoginUseCase.execute(memberCode, password))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("not match password");
        verify(managerAdaptor).queryByMemberCode(memberCode);
        verify(managerValidator).checkPasswordEncode(manager, password);
    }
}
