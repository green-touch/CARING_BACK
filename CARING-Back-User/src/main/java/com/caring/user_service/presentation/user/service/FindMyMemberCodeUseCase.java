package com.caring.user_service.presentation.user.service;

import com.caring.user_service.common.annotation.UseCase;
import com.caring.user_service.domain.user.business.adaptor.UserAdaptor;
import com.caring.user_service.domain.user.entity.User;
import com.caring.user_service.presentation.user.vo.RequestMemberCode;
import com.caring.user_service.presentation.user.vo.ResponseMemberCode;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FindMyMemberCodeUseCase {

    private final UserAdaptor userAdaptor;

    public ResponseMemberCode execute(RequestMemberCode request) {

        User user = userAdaptor.queryUserByNameAndBirthDateAndPhoneNumber(request.getName(),
                request.getBirthDate(), request.getPhoneNumber());

        return new ResponseMemberCode(user.getName(), user.getMemberCode());
    }
}
