package com.caring.user_service.presentation.user.service;

import com.caring.user_service.common.annotation.UseCase;
import com.caring.user_service.domain.user.business.adaptor.UserAdaptor;
import com.caring.user_service.domain.user.entity.User;
import com.caring.user_service.presentation.user.vo.ResponseUserShelterUuid;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetUserShelterUuidUseCase {

    private final UserAdaptor userAdaptor;

    public ResponseUserShelterUuid execute(String userUuid) {
        User user = userAdaptor.queryUserByUserUuid(userUuid);
        return new ResponseUserShelterUuid(user.getShelterUuid());
    }
}
