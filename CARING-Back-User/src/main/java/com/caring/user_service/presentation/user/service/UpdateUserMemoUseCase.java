package com.caring.user_service.presentation.user.service;

import com.caring.user_service.common.annotation.UseCase;
import com.caring.user_service.domain.user.business.adaptor.UserAdaptor;
import com.caring.user_service.domain.user.business.domainService.UserDomainService;
import com.caring.user_service.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class UpdateUserMemoUseCase {

    private final UserAdaptor userAdaptor;
    private final UserDomainService userDomainService;

    public void execute(String userUuid, String memo) {
        User user = userAdaptor.queryUserByUserUuid(userUuid);

        userDomainService.updateMemo(user, memo);
    }
}
