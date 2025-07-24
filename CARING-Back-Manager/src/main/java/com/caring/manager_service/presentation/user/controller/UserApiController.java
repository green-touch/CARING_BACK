package com.caring.manager_service.presentation.user.controller;


import com.caring.manager_service.common.annotation.ManagerCode;
import com.caring.manager_service.infra.user.vo.request.RequestUser;
import com.caring.manager_service.infra.user.vo.request.RequestUserWithShelterUuid;
import com.caring.manager_service.infra.user.vo.response.ResponseUser;
import com.caring.manager_service.infra.user.vo.response.ResponseUserUuid;
import com.caring.manager_service.presentation.user.service.GetUserAccountsInChargeUseCase;
import com.caring.manager_service.presentation.user.service.RegisterUserAccountByManagerUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "[유저 계정(Auth)]")
@Slf4j
@RestController
@RequestMapping("/v1/api/users")
@RequiredArgsConstructor
public class UserApiController {

    private final RegisterUserAccountByManagerUseCase registerUserAccountByManagerUseCase;
    private final GetUserAccountsInChargeUseCase getUserAccountsInChargeUseCase;

    @Operation(summary = "노인 계정을 생성합니다")
    @PostMapping("/shelters/{shelterId}")
    public ResponseEntity<String> registerUserAccountByManager(
            @Parameter(hidden = true) @ManagerCode String managerCode,
            @PathVariable Long shelterId,
            @RequestBody RequestUser requestUser
    ) {
        return ResponseEntity.ok(registerUserAccountByManagerUseCase
                .execute(managerCode, shelterId, requestUser));
    }

    //TODO change read get all user in shelter
    @Operation(summary = "모든 노인 계정을 조회합니다. 이때 super 권한이 필요합니다.")
    @GetMapping("/super")
    public List<Object> getAllUserInShelter() {
        return null;
    }

    @Operation(summary = "담당자가 보호하는 노인 계정을 모두 조회합니다.")
    @GetMapping("/me")
    public List<ResponseUser> getUserAccountsInCharge(@Parameter(hidden = true) @ManagerCode String managerCode) {
        return getUserAccountsInChargeUseCase.execute(managerCode);
    }

    @Operation(summary = "담당자가 보호하는 노인 계정 정보를 수정합니다.")
    @PatchMapping("/{userUuid}/me")
    public Long editUserAccountByManager() {
        return null;
    }

    @Operation(summary = "노인의 계정 정보를 수정합니다. 이때 보호소 내의 노인 계정을 수정하는 권한이 필요합니다.")
    @PatchMapping("/{userUuid}/super")
    public Long editUserAccountBySuperManager() {
        return null;
    }
}
