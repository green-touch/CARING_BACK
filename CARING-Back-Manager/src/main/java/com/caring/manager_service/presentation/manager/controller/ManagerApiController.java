package com.caring.manager_service.presentation.manager.controller;

import com.caring.manager_service.common.annotation.ManagerRoles;
import com.caring.manager_service.common.util.RoleUtil;
import com.caring.manager_service.domain.authority.entity.ManagerRole;
import com.caring.manager_service.presentation.manager.service.AddUserInManagerGroupUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "[매니저(Auth)]")
@Slf4j
@RestController
@RequestMapping("/v1/api/managers")
@RequiredArgsConstructor
public class ManagerApiController {

    private final AddUserInManagerGroupUseCase addUserInManagerGroupUseCase;

    @Operation(summary = "유저를 매니저 관리 관할에 소속시킵니다.")
    @PostMapping("/{managerUuid}/users/{userUuid}")
    public Long addUserToManagerGroup(@PathVariable String managerUuid,
                                      @PathVariable String userUuid,
                                      @ManagerRoles List<String> roles) {
        log.info("유저 [{}]를 매니저 [{}] 그룹에 추가 시도", userUuid, managerUuid);
        RoleUtil.containManagerRole(ManagerRole.SUPER, roles);
        return addUserInManagerGroupUseCase.execute(userUuid, managerUuid);
    }

}
