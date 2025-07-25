package com.caring.user_service.presentation.user.controller;

import com.caring.user_service.common.annotation.UserCode;
import com.caring.user_service.presentation.user.service.AddEmergencyContactUseCase;
import com.caring.user_service.presentation.user.service.DeleteEmergencyContactUseCase;
import com.caring.user_service.presentation.user.service.GetUserHomeInfoUseCase;
import com.caring.user_service.presentation.user.service.GetUserProfileUseCase;
import com.caring.user_service.presentation.user.service.GetUserShelterUuidUseCase;
import com.caring.user_service.presentation.user.service.UpdateEmergencyContactUseCase;
import com.caring.user_service.presentation.user.service.UpdateUserAddressUseCase;
import com.caring.user_service.presentation.user.service.UpdateUserMemoUseCase;
import com.caring.user_service.presentation.user.service.UpdateUserPhoneNumberUseCase;
import com.caring.user_service.presentation.user.vo.RequestAddress;
import com.caring.user_service.presentation.user.vo.RequestContactUuid;
import com.caring.user_service.presentation.user.vo.RequestEmergencyContact;
import com.caring.user_service.presentation.user.vo.RequestEmergencyContactWithContactUuid;
import com.caring.user_service.presentation.user.vo.RequestMemo;
import com.caring.user_service.presentation.user.vo.RequestPhoneNumber;
import com.caring.user_service.presentation.user.vo.ResponseUser;
import com.caring.user_service.presentation.user.vo.ResponseUserHomeInfo;
import com.caring.user_service.presentation.user.vo.ResponseUserShelterUuid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "[회원(AUTH)]")
@Slf4j
@RestController
@RequestMapping("/v1/api/users")
@RequiredArgsConstructor
public class UserApiController {

    private final GetUserProfileUseCase getUserProfileUseCase;
    private final GetUserShelterUuidUseCase getUserShelterUuidUseCase;
    private final GetUserHomeInfoUseCase getUserHomeInfoUseCase;
    private final AddEmergencyContactUseCase addEmergencyContactUseCase;
    private final UpdateUserPhoneNumberUseCase updateUserPhoneNumberUseCase;
    private final UpdateUserAddressUseCase updateUserAddressUseCase;
    private final UpdateUserMemoUseCase updateUserMemoUseCase;
    private final UpdateEmergencyContactUseCase updateEmergencyContactUseCase;
    private final DeleteEmergencyContactUseCase deleteEmergencyContactUseCase;

    @Operation(summary = "특정 유저의 계정을 조회합니다.")
    @GetMapping("/{memberCode}")
    public ResponseUser getUserProfile(@PathVariable String memberCode) {
        return getUserProfileUseCase.execute(memberCode);
    }

    @Operation(
            summary = "내 정보를 조회합니다. (홈 화면 X)",
            description = """
                    프론트엔드는 Authorization 헤더(JWT)만 전송하면 됩니다.
                    Gateway에서 JWT를 파싱하여 자동으로 'member-code' 헤더를 추가하므로,
                    'member-code' 헤더는 프론트에서 명시적으로 포함시킬 필요가 없습니다.
                     포스트맨에서 테스트 가능합니다.""")
    @GetMapping("/my/info")
    public ResponseUser getMyInfo(@Parameter(hidden = true) @UserCode String memberCode) {
        return getUserProfileUseCase.execute(memberCode);
    }

    @Operation(
            summary = "경량화된 내 정보(비상연락망 포함x)를 조회합니다. (홈 화면 O)",
            description = """
                    프론트엔드는 Authorization 헤더(JWT)만 전송하면 됩니다.
                    Gateway에서 JWT를 파싱하여 자동으로 'member-code' 헤더를 추가하므로,
                    'member-code' 헤더는 프론트에서 명시적으로 포함시킬 필요가 없습니다.
                     포스트맨에서 테스트 가능합니다.""")
    @GetMapping("/home/info")
    public ResponseUserHomeInfo getHomeInfo(@Parameter(hidden = true) @UserCode String memberCode) {
        return getUserHomeInfoUseCase.execute(memberCode);
    }

    @Operation(summary = "특정 유저의 shelterUuid를 반환합니다. 유저가 없으면 404를 반환합니다.")
    @GetMapping("/{userUuid}/shelterUuid")
    public ResponseUserShelterUuid getUserShelterUuid(@PathVariable String userUuid) {
        return getUserShelterUuidUseCase.execute(userUuid);
    }

    @Operation(summary = "유저가 비상연락망 직접 추가 beta (관리자가 추가 하는건 권한 문제 해결후 변경 예정) 현재 개발용")
    @PostMapping("/emergency-contacts")
    public ResponseEntity<Void> saveEmergencyContact(@RequestBody RequestEmergencyContact request) {
        addEmergencyContactUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "유저가 비상연락망 삭제 beta (관리자가 수정하는건 권한 문제 해결후 변경 예정) 현재 개발용")
    @DeleteMapping("/emergency-contacts")
    public ResponseEntity<Void> deleteEmergencyContact(@RequestBody RequestContactUuid request) {
        deleteEmergencyContactUseCase.execute(request.getContactUuid());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "유저가 비상연락망 편집 beta (관리자가 수정하는건 권한 문제 해결후 변경 예정) 현재 개발용")
    @PatchMapping("/emergency-contacts")
    public ResponseEntity<Void> updateEmergencyContact(@RequestParam String userUuid, @RequestBody RequestEmergencyContactWithContactUuid request) {
        updateEmergencyContactUseCase.execute(userUuid, request);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "userUuid를 통해 유저의 연락처를 업데이트 합니다.")
    @PatchMapping("/phone-number")
    public ResponseEntity<Void> updateUserPhoneNumber(@RequestParam String userUuid, @RequestBody RequestPhoneNumber request) {
        updateUserPhoneNumberUseCase.execute(userUuid, request.getPhoneNumber());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "userUuid를 통해 유저의 주소를 업데이트 합니다.")
    @PatchMapping("/address")
    public ResponseEntity<Void> updateUserAddress(@RequestParam String userUuid, @RequestBody RequestAddress request) {
        updateUserAddressUseCase.execute(userUuid, request);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "userUuid를 통해 유저의 메모를 추가(업데이트) 합니다.")
    @PatchMapping("/memo")
    public ResponseEntity<Void> updateUserMemo(@RequestParam String userUuid, @RequestBody RequestMemo request) {
        updateUserMemoUseCase.execute(userUuid, request.getMemo());
        return ResponseEntity.noContent().build();
    }
}
