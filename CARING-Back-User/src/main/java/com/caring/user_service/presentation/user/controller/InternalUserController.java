package com.caring.user_service.presentation.user.controller;

import com.caring.user_service.presentation.user.service.*;
import com.caring.user_service.presentation.user.vo.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/internal/users")
@RequiredArgsConstructor
@Tag(name = "회원[INTERNAL]", description = "내부 서버 전용 유저 API/ 해당 스웨거는 개발용입니다! 프론트에서 직접 참조 XXX!!!")
public class InternalUserController {

    private final RegisterUserUseCase registerUserUseCase;
    private final GetUserShelterUuidUseCase getUserShelterUuidUseCase;
    private final GetUsersByUuidListUseCase getUsersByUuidListUseCase;
    private final GetUserDetailInfoUseCase getUserDetailInfoUseCase;

    @Operation(hidden = true)
    @PostMapping("/register")
    public String registerUser(@RequestBody RequestUser request) {
        return registerUserUseCase.execute(request);
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
    @GetMapping("/info")
    public ResponseUserDetailInfo getUserDetailInfo(@RequestParam String memberCode) {
        return getUserDetailInfoUseCase.execute(memberCode);
    }
}
