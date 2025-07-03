package com.caring.manager_service.presentation.manager.service;

import com.caring.manager_service.common.annotation.UseCase;
import com.caring.manager_service.domain.manager.business.adaptor.ManagerAdaptor;
import com.caring.manager_service.common.external.user.UserFeignClient;
import com.caring.manager_service.common.external.user.dto.ResponseUserShelterUuid;
import com.caring.manager_service.domain.manager.business.service.ManagerDomainService;
import com.caring.manager_service.domain.manager.entity.Manager;
import com.caring.manager_service.domain.shelter.business.validator.ShelterValidator;
import feign.FeignException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@UseCase
@Transactional
@RequiredArgsConstructor
public class AddUserInManagerGroupUseCase {

    private final UserFeignClient userFeignClient;
    private final ManagerAdaptor managerAdaptor;
    private final ShelterValidator shelterValidator;
    private final ManagerDomainService managerDomainService;

    public Long execute(String userUuid, String managerUuid) {
        // user의 shelterUuid를 가져오면서 존재 여부도 동시에 체크
        ResponseUserShelterUuid responseUserShelterUuid;
        try {
            responseUserShelterUuid = userFeignClient.getUserShelterUuid(userUuid);
        } catch (FeignException.NotFound e) {
            throw new IllegalArgumentException("User does not exist: " + userUuid);
        }

        // 매니저 정보 조회
        Manager manager = managerAdaptor.queryByManagerUuid(managerUuid);

        // 보호소 일치 여부 검증
        if (!shelterValidator.isSameShelterUserAndManager(responseUserShelterUuid.getShelterUuid(), manager)) {
            throw new IllegalStateException("User and manager belong to different shelters.");
        }

        // 매니저 그룹에 유저 등록
        return managerDomainService.addUserToManagerGroup(manager, responseUserShelterUuid.getShelterUuid()).getId();
    }
}
