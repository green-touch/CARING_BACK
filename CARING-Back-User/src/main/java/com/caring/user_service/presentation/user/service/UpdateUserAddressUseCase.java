package com.caring.user_service.presentation.user.service;

import com.caring.user_service.common.annotation.UseCase;
import com.caring.user_service.domain.user.business.adaptor.UserAdaptor;
import com.caring.user_service.domain.user.business.domainservice.UserDomainService;
import com.caring.user_service.domain.user.entity.User;
import com.caring.user_service.presentation.user.mapper.UserMapper;
import com.caring.user_service.presentation.user.vo.RequestAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class UpdateUserAddressUseCase {

    private final UserAdaptor userAdaptor;
    private final UserDomainService userDomainService;
    private final UserMapper userMapper;

    public void execute(String userUuid, RequestAddress requestAddress) {
        User user = userAdaptor.queryUserByUserUuid(userUuid);

        userDomainService.updateAddress(user, userMapper.toAddressDto(requestAddress));
    }
}
