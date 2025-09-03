package com.caring.manager_service.presentation.user.service;

import com.caring.manager_service.common.annotation.UseCase;
import com.caring.manager_service.domain.manager.business.validator.ManagerValidator;
import com.caring.manager_service.infra.user.client.UserServiceClient;
import com.caring.manager_service.infra.user.vo.request.RequestPhoneNumber;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.caring.manager_service.common.util.RoleUtil.validateAuthOfHandleUser;
import static com.caring.manager_service.domain.authority.entity.SuperAuth.CREATE_OR_DELETE_SENIOR_ACCOUNT;

@UseCase
@RequiredArgsConstructor
public class EditUserPhoneNumberByManagerUseCase {

    private final UserServiceClient userServiceClient;
    private final ManagerValidator managerValidator;


    public void execute(String managerCode,
                        List<String> roles,
                        String userUuid,
                        RequestPhoneNumber requestPhoneNumber) {
        validateAuthOfHandleUser(
                managerValidator.isGroupedUser(managerCode, userUuid),
                CREATE_OR_DELETE_SENIOR_ACCOUNT,
                roles
        );

        userServiceClient.updateUserPhoneNumber(userUuid, requestPhoneNumber);
    }
}
