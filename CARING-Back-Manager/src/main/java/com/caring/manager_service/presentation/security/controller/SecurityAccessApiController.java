package com.caring.manager_service.presentation.security.controller;

import com.caring.manager_service.presentation.security.service.manager.ManagerTokenService;
import com.caring.manager_service.presentation.security.vo.JwtToken;
import com.caring.manager_service.presentation.security.vo.RequestLogin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//유저 로그인 삭제
@Tag(name = "[로그인(매니저)]")
@Slf4j
@RestController
@RequestMapping("/v1/api/access/tokens")
@RequiredArgsConstructor
public class SecurityAccessApiController {

    private final ManagerTokenService managerTokenService;

    @Operation(summary = "매니저가 로그인을 합니다.")
    @PostMapping("/managers/login")
    public ResponseEntity<JwtToken> loginManager(@RequestBody RequestLogin requestLogin) {
        return ResponseEntity
                .ok(managerTokenService.login(requestLogin.getMemberCode(), requestLogin.getPassword()));
    }

}
