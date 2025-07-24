package com.caring.user_service.presentation.user.controller;

import com.caring.user_service.presentation.user.service.FindMyMemberCodeUseCase;
import com.caring.user_service.presentation.user.service.ReadAllUserUseCase;
import com.caring.user_service.presentation.user.service.RegisterUserUseCase;
import com.caring.user_service.presentation.user.service.ResetUserPasswordUseCase;
import com.caring.user_service.presentation.user.vo.RequestMemberCode;
import com.caring.user_service.presentation.user.vo.RequestResetPassword;
import com.caring.user_service.presentation.user.vo.RequestUser;
import com.caring.user_service.presentation.user.vo.ResponseMemberCode;
import com.caring.user_service.presentation.user.vo.ResponseUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "[회원(ACCESS)]")
@RestController
@RequestMapping("/v1/api/access/users")
@RequiredArgsConstructor
public class UserAccessApiController {

    private final RegisterUserUseCase registerUserUseCase;
    private final ReadAllUserUseCase readAllUserUseCase;
    private final FindMyMemberCodeUseCase findMyMemberCodeUseCase;
    private final ResetUserPasswordUseCase resetUserPasswordUseCase;

    //TODO: 유저의 비상연락망 리스트 추가
    @Operation(summary = "새로운 유저를 등록합니다.")
    @PostMapping("/register")
    public String registerUser(@RequestBody RequestUser requestUser) {
        return registerUserUseCase.execute(requestUser);
    }

    @Operation(summary = "모든 유저 목록을 조회합니다.")
    @GetMapping
    public List<ResponseUser> getAllUser() {
        return readAllUserUseCase.execute();
    }

    @PostMapping("/my/member-code")
    @Operation(summary = "핸드폰 인증을 통해 내 memberCode를 조회합니다.")
    public ResponseMemberCode findMyMemberCode(@RequestBody RequestMemberCode request) {
        return findMyMemberCodeUseCase.execute(request);
    }

    @PostMapping("/my/reset-password")
    @Operation(summary = "비밀번호 재설정 (휴대폰 인증 완료 후)")
    public ResponseEntity<Void> resetPassword(@RequestBody @Valid RequestResetPassword request) {
        resetUserPasswordUseCase.execute(request);
        return ResponseEntity.ok().build();
    }
}
