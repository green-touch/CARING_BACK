package com.caring.manager_service.presentation.manager.service;

import com.caring.manager_service.common.external.user.UserFeignClient;
import com.caring.manager_service.common.external.user.dto.ResponseUserShelterUuid;
import com.caring.manager_service.domain.manager.business.adaptor.ManagerAdaptor;
import com.caring.manager_service.domain.manager.business.service.ManagerDomainService;
import com.caring.manager_service.domain.manager.entity.Manager;
import com.caring.manager_service.domain.manager.entity.ManagerGroup;
import com.caring.manager_service.domain.shelter.business.validator.ShelterValidator;
import feign.FeignException;
import feign.Request;
import feign.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddUserInManagerGroupUseCaseTest {

    @Mock
    private UserFeignClient userFeignClient;

    @Mock
    private ManagerAdaptor managerAdaptor;

    @Mock
    private ShelterValidator shelterValidator;

    @Mock
    private ManagerDomainService managerDomainService;

    @InjectMocks
    private AddUserInManagerGroupUseCase addUserInManagerGroupUseCase;

    private String userUuid;
    private String managerUuid;
    private String shelterUuid;
    private Manager manager;

    @BeforeEach
    void setUp() {
        userUuid = "test-user-uuid";
        managerUuid = "manager-uuid";
        shelterUuid = "test-shelter-uuid";

        manager = Manager.builder()
                .managerUuid(managerUuid)
                .shelterUuid(shelterUuid)
                .build();
    }

    @Test
    @DisplayName("유저를 매니저 그룹에 성공적으로 추가한다")
    void addUserToManagerGroupSuccess() {
        // given
        when(userFeignClient.getUserShelterUuid(userUuid))
                .thenReturn(new ResponseUserShelterUuid(shelterUuid));
        when(managerAdaptor.queryByManagerUuid(managerUuid)).thenReturn(manager);
        when(shelterValidator.isSameShelterUserAndManager(shelterUuid, manager)).thenReturn(true);
        when(managerDomainService.addUserToManagerGroup(manager, shelterUuid))
                .thenReturn(ManagerGroup.builder().id(1L).userUuid(userUuid).manager(manager).build());

        // when
        Long result = addUserInManagerGroupUseCase.execute(userUuid, managerUuid);

        // then
        assertThat(result).isEqualTo(1L);
    }

    @Test
    @DisplayName("존재하지 않는 유저 UUID인 경우 예외가 발생한다")
    void addNonExistentUserThrowsException() {
        // given
        Request request = Request.create(Request.HttpMethod.GET, "/mock", Map.of(), null, null, null);
        FeignException notFound = FeignException.errorStatus(
                "getUserShelterUuid",
                Response.builder().status(404).reason("Not Found").request(request).build()
        );
        when(userFeignClient.getUserShelterUuid(userUuid)).thenThrow(notFound);

        // when & then
        assertThatThrownBy(() -> addUserInManagerGroupUseCase.execute(userUuid, managerUuid))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("User does not exist");
    }

    @Test
    @DisplayName("다른 보호소의 유저를 추가하려고 하면 예외가 발생한다")
    void addUserFromDifferentShelterThrowsException() {
        // given
        when(userFeignClient.getUserShelterUuid(userUuid))
                .thenReturn(new ResponseUserShelterUuid(shelterUuid));
        when(managerAdaptor.queryByManagerUuid(managerUuid)).thenReturn(manager);
        when(shelterValidator.isSameShelterUserAndManager(shelterUuid, manager)).thenReturn(false);

        // when & then
        assertThatThrownBy(() -> addUserInManagerGroupUseCase.execute(userUuid, managerUuid))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("User and manager belong to different shelters");
    }

}
