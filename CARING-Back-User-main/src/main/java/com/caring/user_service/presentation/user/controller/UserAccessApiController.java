package com.caring.user_service.presentation.user.controller;

import com.caring.user_service.presentation.user.service.ReadAllUserUseCase;
import com.caring.user_service.presentation.user.service.RegisterUserUseCase;
import com.caring.user_service.presentation.user.vo.RequestUser;
import com.caring.user_service.presentation.user.vo.ResponseUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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

    @Operation(summary = "새로운 유저를 등록합니다.")
    @PostMapping("/register")
    public Long registerUser(@RequestBody RequestUser requestUser) {
        return registerUserUseCase.execute(requestUser);
    }

    @Operation(summary = "모든 유저 목록을 조회합니다.")
    @GetMapping
    public List<ResponseUser> getAllUser() {
        return readAllUserUseCase.execute();
    }
}
