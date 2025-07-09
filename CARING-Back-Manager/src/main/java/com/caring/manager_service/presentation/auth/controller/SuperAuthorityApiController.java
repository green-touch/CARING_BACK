package com.caring.manager_service.presentation.auth.controller;

import com.caring.manager_service.common.annotation.ManagerRoles;
import com.caring.manager_service.common.util.RoleUtil;
import com.caring.manager_service.domain.authority.entity.SuperAuth;
import com.caring.manager_service.presentation.auth.service.SwitchSuperAuthUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "[권한 설정(Auth)]")
@RestController
@RequestMapping("/v1/api/super-authorities")
@RequiredArgsConstructor
public class SuperAuthorityApiController {

    private final SwitchSuperAuthUseCase switchSuperAuthUseCase;

    @Operation(summary = "특정 매니저를 권한 조정합니다.")
    @PatchMapping("/{managerUuid}")
    public void switchSuperAuth(@PathVariable String managerUuid,
                                @RequestParam List<SuperAuth> superAuthList,
                                @ManagerRoles List<String> roles) {
        RoleUtil.containManagerRole(SuperAuth.GRANT_SUPER_MANAGER_PERMISSION, roles);
        switchSuperAuthUseCase.execute(managerUuid, superAuthList);
    }
}
