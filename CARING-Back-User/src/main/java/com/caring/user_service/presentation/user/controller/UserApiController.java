package com.caring.user_service.presentation.user.controller;

import com.caring.user_service.common.annotation.ManagerCode;
import com.caring.user_service.presentation.user.service.GetUserProfileUseCase;
import com.caring.user_service.presentation.user.service.GetUserShelterUuidUseCase;
import com.caring.user_service.presentation.user.vo.ResponseUser;
import com.caring.user_service.presentation.user.vo.ResponseUserShelterUuid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "[회원(AUTH)]")
@Slf4j
@RestController
@RequestMapping("/v1/api/users")
@RequiredArgsConstructor
public class UserApiController {

    private final GetUserProfileUseCase getUserProfileUseCase;
    private final GetUserShelterUuidUseCase getUserShelterUuidUseCase;

    @Operation(summary = "특정 유저의 계정을 조회합니다.")
    @GetMapping("/{userUuid}")
    public ResponseUser getUserProfile(@PathVariable String userUuid,
            @ManagerCode String managerCode) {
        return getUserProfileUseCase.execute(userUuid);
    }

    @Operation(summary = "특정 유저의 shelterUuid를 반환합니다. 유저가 없으면 404를 반환합니다.")
    @GetMapping("/{userUuid}/shelterUuid")
    public ResponseUserShelterUuid getUserShelterUuid(@PathVariable String userUuid,
            @ManagerCode String managerCode) {
        return getUserShelterUuidUseCase.execute(userUuid);
    }

}
