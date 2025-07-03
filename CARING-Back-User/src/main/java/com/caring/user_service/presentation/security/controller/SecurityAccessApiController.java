package com.caring.user_service.presentation.security.controller;

import com.caring.user_service.presentation.security.service.user.UserTokenService;
import com.caring.user_service.presentation.security.vo.JwtToken;
import com.caring.user_service.presentation.security.vo.RequestLogin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "[로그인(유저)]")
@Slf4j
@RestController
@RequestMapping("/v1/api/access/tokens")
@RequiredArgsConstructor
public class SecurityAccessApiController {

    private final UserTokenService userTokenService;

    @Operation(summary = "유저가 로그인을 합니다.")
    @PostMapping("/users")
    public JwtToken loginUser(@RequestBody RequestLogin requestLogin) {
        return userTokenService.login(requestLogin.getMemberCode(), requestLogin.getPassword());
    }

}
