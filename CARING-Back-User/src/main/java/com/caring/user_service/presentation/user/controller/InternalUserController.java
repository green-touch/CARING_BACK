package com.caring.user_service.presentation.user.controller;

import com.caring.user_service.presentation.user.service.*;
import com.caring.user_service.presentation.user.vo.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/internal/users")
@RequiredArgsConstructor
@Tag(name = "회원[INTERNAL]", description = "내부 서버 전용 유저 API/ 해당 스웨거는 개발용입니다! 프론트에서 직접 참조 XXX!!!")
public class InternalUserController {

    private final RegisterUserByManagerUseCase registerUserByManagerUseCase;
    private final GetUserShelterUuidUseCase getUserShelterUuidUseCase;
    private final GetUsersByUuidListUseCase getUsersByUuidListUseCase;
    private final GetUserDetailInfoUseCase getUserDetailInfoUseCase;
    private final AddEmergencyContactUseCase addEmergencyContactUseCase;
    private final DeleteEmergencyContactUseCase deleteEmergencyContactUseCase;
    private final UpdateEmergencyContactUseCase updateEmergencyContactUseCase;
    private final UpdateUserPhoneNumberUseCase updateUserPhoneNumberUseCase;
    private final UpdateUserAddressUseCase updateUserAddressUseCase;
    private final UpdateUserMemoUseCase updateUserMemoUseCase;

    @Operation(hidden = true)
    @PostMapping("/register")
    public ResponseUserUuid registerUser(@RequestBody RequestUserWithShelterUuid request) {
        return registerUserByManagerUseCase.execute(request);
    }

    @Operation(hidden = true)
    @GetMapping("/{userUuid}/shelterUuid")
    public ResponseUserShelterUuid getUserShelterUuid(@PathVariable String userUuid) {
        return getUserShelterUuidUseCase.execute(userUuid);
    }

    @Operation(hidden = true)
    @GetMapping
    public List<ResponseUser> queryUserByUuidList(@RequestBody List<String> uuidList) {
        return getUsersByUuidListUseCase.execute(uuidList);
    }

    @Operation(
            summary = "유저 상세 정보 조회",
            description = "관리 페이지에서 매니저가 memberCode를 기반으로 유저의 상세 정보를 조회합니다." +
                    "매니저의 권한은 ManagerService에서 확인 후 해당 API를 이용합니다.",
            tags = {"회원[INTERNAL]"} // 태그 지정
    )
    @GetMapping("/info/{userUuid}")
    public ResponseUserDetailInfo getUserDetailInfo(@PathVariable String userUuid) {
        return getUserDetailInfoUseCase.execute(userUuid);
    }

    @Operation(hidden = true)
    @PostMapping("/{userUuid}/emergency-contacts")
    public void saveEmergencyContact(@PathVariable String userUuid,
                                     @RequestBody RequestEmergencyContact request) {
        addEmergencyContactUseCase.execute(userUuid, request);
    }

    @Operation(hidden = true)
    @DeleteMapping("/emergency-contacts/{contactUuid}")
    public void deleteEmergencyContact(@PathVariable String contactUuid) {
        deleteEmergencyContactUseCase.execute(contactUuid);
    }

    @Operation(hidden = true)
    @PatchMapping("/emergency-contacts/{contactUuid}")
    public void updateEmergencyContact(
            @PathVariable String contactUuid,
            @RequestBody RequestEmergencyContactWithContactUuid request) {
        updateEmergencyContactUseCase.execute(contactUuid, request);
    }

    @Operation(hidden = true)
    @PatchMapping("/{userUuid}/phone-number")
    public void updateUserPhoneNumber(@PathVariable String userUuid,
                                                      @RequestBody RequestPhoneNumber request) {
        updateUserPhoneNumberUseCase.execute(userUuid, request.getPhoneNumber());
    }

    @Operation(hidden = true)
    @PatchMapping("/{userUuid}/address")
    public void updateUserAddress(@PathVariable String userUuid,
                                  @RequestBody RequestAddress request) {
        updateUserAddressUseCase.execute(userUuid, request);
    }

    @Operation(hidden = true)
    @PatchMapping("/{userUuid}/memo")
    public void updateUserMemo(@PathVariable String userUuid,
                               @RequestBody RequestMemo request) {

        updateUserMemoUseCase.execute(userUuid, request.getMemo());
    }
}
