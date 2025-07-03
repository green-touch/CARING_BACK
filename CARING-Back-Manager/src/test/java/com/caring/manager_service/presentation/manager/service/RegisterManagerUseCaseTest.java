package com.caring.manager_service.presentation.manager.service;

import com.caring.manager_service.domain.authority.business.adaptor.AuthorityAdaptor;
import com.caring.manager_service.domain.authority.entity.Authority;
import com.caring.manager_service.domain.authority.entity.ManagerRole;
import com.caring.manager_service.domain.manager.business.service.ManagerDomainService;
import com.caring.manager_service.domain.manager.entity.Manager;
import com.caring.manager_service.presentation.manager.vo.request.RequestManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class RegisterManagerUseCaseTest {
    @Mock
    private ManagerDomainService managerDomainService;

    @Mock
    private AuthorityAdaptor authorityAdaptor;

    private RegisterManagerUseCase registerManagerUseCase;

    private RequestManager requestManager;
    private Authority authority;
    private Manager manager;

    @BeforeEach
    void setup() {
        registerManagerUseCase = new RegisterManagerUseCase(managerDomainService, authorityAdaptor);

        // 테스트 데이터 설정
        requestManager = RequestManager.builder()
                .name("test-manager")
                .password("test-password")
                .build();

        authority = Authority.builder()
                .managerRole(ManagerRole.MANAGE)
                .build();

        manager = Manager.builder()
                .id(1L)
                .name(requestManager.getName())
                .password(requestManager.getPassword())
                .build();
    }

    @Test
    @DisplayName("매니저 등록에 성공한다")
    void registerManagerSuccess() {
        // given
        when(authorityAdaptor.queryByManagerRole(ManagerRole.MANAGE)).thenReturn(authority);
        when(managerDomainService.registerManager(
                requestManager.getName(),
                requestManager.getPassword(),
                authority)).thenReturn(manager);

        // when
        Long managerId = registerManagerUseCase.execute(requestManager);

        // then
        assertThat(managerId).isEqualTo(manager.getId());
        verify(authorityAdaptor).queryByManagerRole(ManagerRole.MANAGE);
        verify(managerDomainService).registerManager(
                requestManager.getName(),
                requestManager.getPassword(),
                authority);
    }

}
