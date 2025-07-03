package com.caring.user_service.presentation.user.service;

import com.caring.user_service.common.annotation.UseCase;
import com.caring.user_service.domain.user.business.adaptor.UserAdaptor;
import com.caring.user_service.domain.user.business.validator.UserValidator;
import com.caring.user_service.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserLoginUseCase {
    private final UserAdaptor userAdaptor;
    private final UserValidator userValidator;

    public User execute(String memberCode, String password) {
        User findUser = userAdaptor.queryUserByMemberCode(memberCode);
        userValidator.checkPasswordEncode(findUser, password);
        return findUser;
    }
}
