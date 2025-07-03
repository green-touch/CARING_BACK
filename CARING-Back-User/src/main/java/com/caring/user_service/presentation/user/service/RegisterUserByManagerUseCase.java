package com.caring.user_service.presentation.user.service;

import com.caring.user_service.common.annotation.UseCase;
import com.caring.user_service.domain.user.business.domainservice.UserDomainService;
import com.caring.user_service.domain.user.entity.User;
import com.caring.user_service.presentation.user.vo.RequestUserWithShelterUuid;
import com.caring.user_service.presentation.user.vo.ResponseUserUuid;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class RegisterUserByManagerUseCase {
    private final UserDomainService userDomainService;

    public ResponseUserUuid execute(RequestUserWithShelterUuid requestUser) {
        User user = userDomainService.registerUserWithShelterUuid(
                    requestUser.getName(), requestUser.getPassword(), requestUser.getShelterUuid());
        return new ResponseUserUuid(user.getId(), user.getUserUuid());
    }
}
