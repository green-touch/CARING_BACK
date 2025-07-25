package com.caring.manager_service.presentation.manager.controller;

import com.caring.manager_service.common.annotation.ManagerCode;
import com.caring.manager_service.common.annotation.ManagerRoles;
import com.caring.manager_service.common.util.RoleUtil;
import com.caring.manager_service.domain.authority.entity.SuperAuth;
import com.caring.manager_service.infra.user.vo.request.RequestUser;
import com.caring.manager_service.infra.user.vo.response.ResponseUser;
import com.caring.manager_service.infra.user.vo.response.ResponseUserDetailInfo;
import com.caring.manager_service.presentation.manager.service.*;
import com.caring.manager_service.presentation.manager.vo.request.EditManagerInform;
import com.caring.manager_service.presentation.manager.vo.request.RequestManager;
import com.caring.manager_service.presentation.manager.vo.response.ResponseSpecificManager;
import com.caring.manager_service.presentation.user.service.RegisterUserAccountByManagerUseCase;
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
//    private final RegisterUserAccountByManagerUseCase registerUserAccountByManagerUseCase;
//    private final GetUserInfoByManagerUseCase getUserInfoByManagerUseCase;
//    private final GetGroupedUserListByManagerUseCase getGroupedUserListByManagerUseCase;

    @Operation(summary = "일반 매니저 계정을 생성합니다. 이때 생성자는 매니저 생성 권한이 필요합니다.")
    @PostMapping("/default")
    public ResponseEntity<Long> registerDefaultManagerBySuper(
            @Parameter(hidden = true) @ManagerRoles List<String> roles,
            @RequestBody RequestManager requestManager) {
        return ResponseEntity.ok(registerDefaultManagerBySuperManagerUseCase.execute(roles, requestManager));
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

//    @Operation(summary = "노인 계정을 생성합니다.")
//    @PostMapping("/users")
//    public ResponseEntity<String> registerUserAccountByManager(@Parameter(hidden = true) @ManagerRoles List<String> managerRoles,
//                                                               @RequestBody RequestUser requestUser) {
//        return ResponseEntity.ok(registerUserAccountByManagerUseCase.execute(managerRoles, requestUser));
//    }

    //TODO path variable
//    @Operation(summary = "노인 계정을 조회합니다. 이때 본인이 관리중인 노인이거나, 관리자의 권한에 노인 조회 권한이 존재할 때 가능합니다.")
//    @GetMapping("/users")
//    public ResponseEntity<ResponseUserDetailInfo> getUserInfoByManager(@RequestParam String userCode,
//                                                                       @Parameter(hidden = true) @ManagerCode String managerCode,
//                                                                       @Parameter(hidden = true) @ManagerRoles List<String> roles) {
//        return ResponseEntity.ok(getUserInfoByManagerUseCase.execute(managerCode, roles, userCode));
//    }
//
//    @Operation(summary = "매니저가 담당중인 노인 계정을 조회합니다.")
//    @GetMapping("/users/grouped")
//    public ResponseEntity<List<ResponseUser>> getGroupedUserListByManager(@Parameter(hidden = true) @ManagerCode String managerCode) {
//        return ResponseEntity.ok(getGroupedUserListByManagerUseCase.execute(managerCode));
//    }
}
