package com.caring.manager_service.presentation.manager.service;

import com.caring.manager_service.common.external.user.UserFeignClient;
import com.caring.manager_service.common.external.user.dto.RequestUserRegister;
import com.caring.manager_service.common.external.user.dto.ResponseUserUuid;
import com.caring.manager_service.domain.shelter.business.adaptor.ShelterAdaptor;
import com.caring.manager_service.domain.shelter.business.service.ShelterDomainService;
import com.caring.manager_service.domain.shelter.entity.Shelter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterUserByManagerUseCaseTest {

    @Mock
    private UserFeignClient userFeignClient;

    @Mock
    private ShelterAdaptor shelterAdaptor;

    @Mock
    private ShelterDomainService shelterDomainService;

    private RegisterUserByManagerUseCase registerUserByManagerUseCase;

    private RequestUserRegister baseRequest;
    private String shelterUuid;
    private Shelter mockShelter;
    private ResponseUserUuid mockResponse;

    @BeforeEach
    void setUp() {
        registerUserByManagerUseCase = new RegisterUserByManagerUseCase(
                userFeignClient,
                shelterAdaptor,
                shelterDomainService
        );

        shelterUuid = "test-shelter-uuid";
        baseRequest = new RequestUserRegister("test-user", "test-password", null);
        mockShelter = Shelter.builder().shelterUuid(shelterUuid).build();
        mockResponse = new ResponseUserUuid(1L, "test-user-uuid");
    }

    @Test
    @DisplayName("유저 등록에 성공한다")
    void registerUserSuccess() {
        // given
        RequestUserRegister requestWithShelter = new RequestUserRegister(
                baseRequest.getName(),
                baseRequest.getPassword(),
                shelterUuid
        );

        when(userFeignClient.registerUser(requestWithShelter)).thenReturn(mockResponse);
        when(shelterAdaptor.queryByShelterUuid(shelterUuid)).thenReturn(mockShelter);

        // when
        Long userId = registerUserByManagerUseCase.execute(baseRequest, shelterUuid);

        // then
        assertThat(userId).isEqualTo(mockResponse.getUserId());
        verify(userFeignClient).registerUser(requestWithShelter);
        verify(shelterAdaptor).queryByShelterUuid(shelterUuid);
        verify(shelterDomainService).addShelterGroup(shelterUuid, mockResponse.getUserUuid());
    }

    @Test
    @DisplayName("존재하지 않는 보호소에 유저 등록 시도 시 예외가 발생한다")
    void registerUserWithNonExistentShelterThrowsException() {
        // given
        when(shelterAdaptor.queryByShelterUuid(shelterUuid))
                .thenThrow(new IllegalArgumentException("Shelter not found with UUID: " + shelterUuid));

        // when & then
        assertThatThrownBy(() -> registerUserByManagerUseCase.execute(baseRequest, shelterUuid))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Shelter not found with UUID: " + shelterUuid);

        // then
        verify(shelterAdaptor).queryByShelterUuid(shelterUuid);
        verifyNoInteractions(userFeignClient);
        verifyNoInteractions(shelterDomainService);
    }

}
