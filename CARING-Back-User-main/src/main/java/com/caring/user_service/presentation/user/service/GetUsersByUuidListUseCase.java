package com.caring.user_service.presentation.user.service;

import com.caring.user_service.common.annotation.UseCase;
import com.caring.user_service.domain.user.business.adaptor.UserAdaptor;
import com.caring.user_service.domain.user.entity.User;
import com.caring.user_service.presentation.user.mapper.UserMapper;
import com.caring.user_service.presentation.user.vo.ResponseUser;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@UseCase
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetUsersByUuidListUseCase {

    private final UserAdaptor userAdaptor;

    public List<ResponseUser> execute(List<String> uuidList) {
        List<User> users = userAdaptor.queryByUserUuidList(uuidList);

        return users.stream()
                .map(UserMapper.INSTANCE::toResponseUserVo)
                .toList();
    }
}
