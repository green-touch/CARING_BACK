package com.caring.user_service.presentation.user.service;

import com.caring.user_service.common.annotation.UseCase;
import com.caring.user_service.domain.user.business.adaptor.UserAdaptor;
import com.caring.user_service.domain.user.business.domainservice.UserDomainService;
import com.caring.user_service.domain.user.entity.User;
import com.caring.user_service.presentation.user.vo.RequestAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class UpdateUserAddressUseCase {

    private final UserAdaptor userAdaptor;
    private final UserDomainService userDomainService;

    public void execute(String memberCode, RequestAddress requestAddress) {
        User user = userAdaptor.queryUserByMemberCode(memberCode);

        userDomainService.updateAddress(user, requestAddress.getRoadAddress(), requestAddress.getDetailAddress(),
                requestAddress.getPostalCode());
    }
}
