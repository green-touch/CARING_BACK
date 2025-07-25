//package com.caring.manager_service.presentation.manager.service;
//
//import com.caring.manager_service.common.annotation.UseCase;
//import com.caring.manager_service.domain.manager.business.adaptor.ManagerAdaptor;
//import com.caring.manager_service.infra.user.client.UserServiceClient;
//import com.caring.manager_service.infra.user.vo.response.ResponseUser;
//import lombok.RequiredArgsConstructor;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@UseCase
//@Transactional(readOnly = true)
//@RequiredArgsConstructor
//public class GetGroupedUserListByManagerUseCase {
//
//    private final ManagerAdaptor managerAdaptor;
//    private final UserServiceClient userServiceClient;
//    public List<ResponseUser> execute(String managerCode) {
//        List<String> userUuidsByManager = managerAdaptor.queryUserUuidsByManager(managerCode);
//        return userServiceClient.queryUserByUuidList(userUuidsByManager);
//    }
//}
