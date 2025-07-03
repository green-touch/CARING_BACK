package com.caring.manager_service.presentation.manager.service;

import com.caring.manager_service.common.annotation.UseCase;
import com.caring.manager_service.common.external.user.UserFeignClient;
import com.caring.manager_service.common.external.user.dto.RequestUserRegister;
import com.caring.manager_service.common.external.user.dto.ResponseUserUuid;
import com.caring.manager_service.domain.shelter.business.adaptor.ShelterAdaptor;
import com.caring.manager_service.domain.shelter.business.service.ShelterDomainService;
import com.caring.manager_service.domain.shelter.entity.Shelter;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class RegisterUserByManagerUseCase {

    private final UserFeignClient userFeignClient;
    private final ShelterAdaptor shelterAdaptor;
    private final ShelterDomainService shelterDomainService;

    public Long execute(RequestUserRegister requestUser, String shelterUuid) {
        // 1. shelter로 shelter가 있는지 사전 검증
        Shelter shelter = shelterAdaptor.queryByShelterUuid(shelterUuid);

        // 2. User 등록은 user-service에 위임
        RequestUserRegister registerDto = new RequestUserRegister(
                requestUser.getName(),
                requestUser.getPassword(),
                shelterUuid
        );
        ResponseUserUuid userUuid = userFeignClient.registerUser(registerDto);

        // 3. shelter 연동 로직은 manager-service 책임
        shelterDomainService.addShelterGroup(shelter.getShelterUuid(), userUuid.getUserUuid()); // uuid기반

        return userUuid.getUserId();
    }
}
