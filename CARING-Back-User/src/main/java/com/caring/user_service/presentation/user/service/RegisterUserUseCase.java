package com.caring.user_service.presentation.user.service;

import com.caring.user_service.common.annotation.UseCase;
import com.caring.user_service.domain.user.business.domainservice.UserDomainService;
import com.caring.user_service.presentation.user.vo.RequestUser;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class RegisterUserUseCase {
    private final UserDomainService userDomainService;

    public String execute(RequestUser requestUser) {
        return userDomainService.registerUser(requestUser.getName(), requestUser.getPassword(),
                requestUser.getBirthdate(), requestUser.getPhoneNumber(),
                requestUser.getRoadAddress(),requestUser.getDetailAddress()).getMemberCode();
    }
}
