package com.caring.manager_service.presentation.user.controller;


import com.caring.manager_service.common.annotation.ManagerCode;
import com.caring.manager_service.common.annotation.ManagerRoles;
import com.caring.manager_service.infra.user.vo.RequestEmergencyContact;
import com.caring.manager_service.infra.user.vo.request.RequestEmergencyContactWithContactUuid;
import com.caring.manager_service.infra.user.vo.request.RequestUser;
import com.caring.manager_service.infra.user.vo.request.RequestUserWithShelterUuid;
import com.caring.manager_service.infra.user.vo.response.ResponseUser;
import com.caring.manager_service.infra.user.vo.response.ResponseUserDetailInfo;
import com.caring.manager_service.infra.user.vo.response.ResponseUserUuid;
import com.caring.manager_service.presentation.user.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    private final GetUserDetailInfoByManagerUseCase getUserDetailInfoByManagerUseCase;
    private final SaveEmergencyContactByManagerUseCase saveEmergencyContactByManagerUseCase;
    private final EditEmergencyContactByManagerUseCase editEmergencyContactByManagerUseCase;
    private final RemoveEmergencyContactByManagerUseCase removeEmergencyContactByManagerUseCase;

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
    @Operation(summary = "모든 노인 계정을 조회합니다. 이때 super 권한이 필요합니다.[구현 x]")
    @GetMapping("/super")
    public List<Object> getAllUserInShelter() {
        return null;
    }

    @Operation(summary = "담당자가 보호하는 노인 계정을 모두 조회합니다.")
    @GetMapping("/me")
    public List<ResponseUser> getUserAccountsInCharge(@Parameter(hidden = true) @ManagerCode String managerCode) {
        return getUserAccountsInChargeUseCase.execute(managerCode);
    }

    @Operation(summary = "담당자가 보호하는 노인의 긴급 연락처 정보를 추가합니다. 이때 super 권한이 존재한다면 담당자가 아니어도 추가 가능합니다.")
    @PostMapping("/{userUuid}/emergency-contact")
    public ResponseEntity<HttpStatus> saveEmergencyContactByManager(@Parameter(hidden = true) @ManagerCode String managerCode,
                                                                    @ManagerRoles List<String> roles,
                                                                    @PathVariable String userUuid,
                                                                    @RequestBody RequestEmergencyContact requestEmergencyContact) {
        saveEmergencyContactByManagerUseCase.execute(managerCode, roles, userUuid, requestEmergencyContact);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @Operation(summary = "담당자가 보호하는 노인의 긴급 연락처 정보를 수정합니다. 이때 super 권한이 존재한다면 담당자가 아니어도 가능합니다.")
    @PatchMapping("/{userUuid}/emergency-contact/{contactUuid}")
    public ResponseEntity<HttpStatus> editEmergencyContactByManager(@Parameter(hidden = true) @ManagerCode String managerCode,
                                                                    @ManagerRoles List<String> roles,
                                                                    @PathVariable String userUuid,
                                                                    @PathVariable String contactUuid,
                                                                    @RequestBody RequestEmergencyContactWithContactUuid requestEmergencyContact) {
        editEmergencyContactByManagerUseCase.execute(
                managerCode,
                roles,
                userUuid,
                contactUuid,
                requestEmergencyContact);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @Operation(summary = "담당자가 보호하는 노인의 긴급 연락처 정보를 삭제합니다. 이때 super 권한이 존재한다면 담당자가 아니어도 가능합니다.")
    @DeleteMapping("/{userUuid}/emergency-contact/{contactUuid}")
    public ResponseEntity<HttpStatus> removeEmergencyContactByManager(@Parameter(hidden = true) @ManagerCode String managerCode,
                                                                      @ManagerRoles List<String> roles,
                                                                      @PathVariable String userUuid,
                                                                      @PathVariable String contactUuid) {
        removeEmergencyContactByManagerUseCase.execute(managerCode, roles, userUuid, contactUuid);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }


    @Operation(summary = "노인 계정을 구체적으로 조회합니다.")
    @GetMapping("/{userUuid}")
    public ResponseEntity<ResponseUserDetailInfo> getUserDetailInfoByManager(
            @PathVariable String userUuid,
            @ManagerRoles List<String> roles,
            @Parameter(hidden = true) @ManagerCode String managerCode) {
        return ResponseEntity.ok(getUserDetailInfoByManagerUseCase.execute(managerCode, roles,  userUuid));
    }
}
