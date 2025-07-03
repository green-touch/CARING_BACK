package com.caring.manager_service.presentation.manager.service;

import com.caring.manager_service.common.annotation.UseCase;
import com.caring.manager_service.common.external.user.UserFeignClient;
import com.caring.manager_service.domain.manager.business.adaptor.ManagerAdaptor;
import com.caring.manager_service.domain.manager.entity.Manager;
import com.caring.manager_service.presentation.manager.vo.response.ResponseUser;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@UseCase
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetUsersOfManagerGroupUseCase {

    private final ManagerAdaptor managerAdaptor;
    private final UserFeignClient userFeignClient;

    public List<ResponseUser> execute(String memberCode) {
        Manager manager = managerAdaptor.queryByMemberCode(memberCode);
        List<String> userUuids = managerAdaptor.queryUserUuidsByManager(manager);

        if (userUuids.isEmpty()) {
            return List.of(); // 빈 리스트 반환
        }

        return userFeignClient.queryUsersByUuidList(userUuids);
    }
}
