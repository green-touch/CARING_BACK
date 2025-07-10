package com.caring.user_service.presentation.user.controller;

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
import org.springframework.web.bind.annotation.RequestHeader;
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
    public ResponseUser getMyInfo(@RequestHeader("member-code") String memberCode) {
        return getUserProfileUseCase.execute(memberCode);
    }



    @Operation(summary = "특정 유저의 shelterUuid를 반환합니다. 유저가 없으면 404를 반환합니다.")
    @GetMapping("/{userUuid}/shelterUuid")
    public ResponseUserShelterUuid getUserShelterUuid(@PathVariable String userUuid) {
        return getUserShelterUuidUseCase.execute(userUuid);
    }

}
