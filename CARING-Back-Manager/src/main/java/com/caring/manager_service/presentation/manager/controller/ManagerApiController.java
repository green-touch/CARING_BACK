package com.caring.manager_service.presentation.manager.controller;

import com.caring.manager_service.common.annotation.ManagerCode;
import com.caring.manager_service.common.annotation.ManagerRoles;
import com.caring.manager_service.common.util.RoleUtil;
import com.caring.manager_service.domain.authority.entity.SuperAuth;
import com.caring.manager_service.presentation.manager.service.EditManagerInformUseCase;
import com.caring.manager_service.presentation.manager.service.GetManagerProfileUseCase;
import com.caring.manager_service.presentation.manager.service.RegisterDefaultManagerBySuperManagerUseCase;
import com.caring.manager_service.presentation.manager.vo.request.EditManagerInform;
import com.caring.manager_service.presentation.manager.vo.request.RequestManager;
import com.caring.manager_service.presentation.manager.vo.response.ResponseSpecificManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/managers")
@RequiredArgsConstructor
public class ManagerApiController {

    private final RegisterDefaultManagerBySuperManagerUseCase registerDefaultManagerBySuperManagerUseCase;
    private final GetManagerProfileUseCase getManagerProfileUseCase;
    private final EditManagerInformUseCase editManagerInformUseCase;

    @Operation(summary = "일반 매니저 계정을 생성합니다. 이때 생성자는 매니저 생성 권한이 필요합니다.")
    @PostMapping("/default")
    public ResponseEntity<Long> registerDefaultManagerBySuper(
            @Parameter(hidden = true) @ManagerRoles List<String> roles,
            @RequestBody RequestManager requestManager) {
        RoleUtil.containManagerRole(SuperAuth.CREATE_MANAGER_ACCOUNT, roles);
        return ResponseEntity.ok(registerDefaultManagerBySuperManagerUseCase.execute(requestManager));
    }

    @Operation(summary = "매니저의 프로필 정보를 조회합니다.")
    @GetMapping("/profiles")
    public ResponseEntity<ResponseSpecificManager> getManagerProfile(@ManagerCode String managerCode){
        return ResponseEntity.ok(getManagerProfileUseCase.execute(managerCode));
    }

    @Operation(summary = "매니저 프로필 정보를 수정합니다")
    @PatchMapping("/profiles")
    public ResponseEntity<Long> editManagerInformation(
            @ManagerCode String managerCode,
            @RequestBody EditManagerInform editManagerInform
    ) {
        return ResponseEntity.ok(editManagerInformUseCase.execute(managerCode, editManagerInform));
    }
}
