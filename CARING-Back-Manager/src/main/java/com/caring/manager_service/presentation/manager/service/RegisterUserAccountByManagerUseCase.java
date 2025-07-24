//package com.caring.manager_service.presentation.manager.service;
//
//import com.caring.manager_service.common.annotation.UseCase;
//import com.caring.manager_service.common.util.RoleUtil;
//import com.caring.manager_service.domain.authority.entity.SuperAuth;
//import com.caring.manager_service.infra.user.client.UserServiceClient;
//import com.caring.manager_service.infra.user.vo.request.RequestUser;
//import lombok.RequiredArgsConstructor;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@UseCase
//@Transactional
//@RequiredArgsConstructor
//public class RegisterUserAccountByManagerUseCase {
//
//    private final UserServiceClient userServiceClient;
//
//    public String execute(List<String> managerRole, RequestUser requestUser){
//        RoleUtil.containManagerRole(SuperAuth.CREATE_OR_DELETE_SENIOR_ACCOUNT, managerRole);
//        return userServiceClient.registerUser(requestUser);
//    }
//}
