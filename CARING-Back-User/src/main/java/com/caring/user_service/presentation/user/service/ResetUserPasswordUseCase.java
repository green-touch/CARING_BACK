package com.caring.user_service.presentation.user.service;

import com.caring.user_service.common.annotation.UseCase;
import com.caring.user_service.domain.user.business.adaptor.UserAdaptor;
import com.caring.user_service.domain.user.business.domainservice.UserDomainService;
import com.caring.user_service.domain.user.entity.User;
import com.caring.user_service.presentation.user.vo.RequestResetPassword;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@UseCase
@Transactional
@RequiredArgsConstructor
public class ResetUserPasswordUseCase {

    private final UserAdaptor userAdaptor;
    private final PasswordEncoder passwordEncoder;
    private final UserDomainService userDomainService;

    public void execute(RequestResetPassword request) {
        User user = userAdaptor.queryUserByMemberCode(request.getMemberCode());
        String encodedPassword = passwordEncoder.encode(request.getNewPassword());

        userDomainService.resetPassword(user, encodedPassword);
    }
}
