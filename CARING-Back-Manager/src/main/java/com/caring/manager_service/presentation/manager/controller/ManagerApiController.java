package com.caring.manager_service.presentation.manager.controller;

import com.caring.manager_service.common.annotation.ManagerRoles;
import com.caring.manager_service.common.util.RoleUtil;
import com.caring.manager_service.domain.authority.entity.SuperAuth;
import com.caring.manager_service.presentation.manager.service.RegisterDefaultManagerBySuperManagerUseCase;
import com.caring.manager_service.presentation.manager.vo.request.RequestManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/api/managers")
@RequiredArgsConstructor
public class ManagerApiController {

    private final RegisterDefaultManagerBySuperManagerUseCase registerDefaultManagerBySuperManagerUseCase;

    @Operation(summary = "일반 매니저 계정을 생성합니다. 이때 생성자는 매니저 생성 권한이 필요합니다.")
    @PostMapping("/default")
    public ResponseEntity<Long> registerDefaultManagerBySuper(@Parameter(hidden = true) @ManagerRoles List<String> roles,
                                              @RequestBody RequestManager requestManager) {
        RoleUtil.containManagerRole(SuperAuth.CREATE_MANAGER_ACCOUNT, roles);
        return ResponseEntity.ok(registerDefaultManagerBySuperManagerUseCase.execute(requestManager));
    }
}
