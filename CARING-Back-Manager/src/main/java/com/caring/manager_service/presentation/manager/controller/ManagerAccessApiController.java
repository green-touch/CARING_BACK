package com.caring.manager_service.presentation.manager.controller;

import com.caring.manager_service.common.annotation.ManagerRoles;
import com.caring.manager_service.common.util.EnumConvertUtil;
import com.caring.manager_service.common.util.RoleUtil;
import com.caring.manager_service.domain.authority.entity.SuperAuth;
import com.caring.manager_service.presentation.manager.service.RegisterDefaultManagerBySuperManagerUseCase;
import com.caring.manager_service.presentation.manager.service.RegisterSuperManagerUseCase;
import com.caring.manager_service.presentation.manager.vo.request.RequestManager;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/access/managers")
@RequiredArgsConstructor
public class ManagerAccessApiController {

    private final RegisterSuperManagerUseCase registerSuperManagerUseCase;

    @Operation(summary = "super 권한을 모두 가진 manager를 생성합니다. 테스트용입니다.")
    @PostMapping("/super")
    public ResponseEntity<Long> registerSuperManager(@RequestBody RequestManager requestManager) {
        return ResponseEntity.ok(registerSuperManagerUseCase.execute(requestManager));
    }

    @Operation(summary = "super 권한 enum을 조회합니다.")
    @GetMapping("/roles")
    public ResponseEntity<List<SuperAuth>> getAllSuperAuth() {
        return ResponseEntity.ok(EnumConvertUtil.getList(SuperAuth.class));
    }
}
