package com.caring.user_service.presentation.user.service;

import com.caring.user_service.common.annotation.UseCase;
import com.caring.user_service.domain.user.business.domainservice.UserDomainService;
import com.caring.user_service.presentation.user.mapper.UserMapper;
import com.caring.user_service.presentation.user.vo.RequestUser;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class RegisterUserUseCase {
    private final UserDomainService userDomainService;
    private final UserMapper userMapper;

    public String execute(RequestUser requestUser) {
        return userDomainService.registerUser(userMapper.toDTO(requestUser)).getMemberCode();
    }
}
