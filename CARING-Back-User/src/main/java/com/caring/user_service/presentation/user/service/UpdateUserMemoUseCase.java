package com.caring.user_service.presentation.user.service;

import com.caring.user_service.common.annotation.UseCase;
import com.caring.user_service.domain.user.business.adaptor.UserAdaptor;
import com.caring.user_service.domain.user.business.domainservice.UserDomainService;
import com.caring.user_service.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class UpdateUserMemoUseCase {

    private final UserAdaptor userAdaptor;
    private final UserDomainService userDomainService;

    public void execute(String memberCode, String memo) {
        User user = userAdaptor.queryUserByMemberCode(memberCode);

        userDomainService.updateMemo(user, memo);
    }
}
