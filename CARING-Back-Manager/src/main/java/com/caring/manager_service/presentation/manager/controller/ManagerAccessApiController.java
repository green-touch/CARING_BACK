package com.caring.manager_service.presentation.manager.controller;

import com.caring.manager_service.presentation.manager.service.RegisterSuperManagerUseCase;
import com.caring.manager_service.presentation.manager.vo.request.RequestManager;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/managers")
@RequiredArgsConstructor
public class ManagerAccessApiController {

    private final RegisterSuperManagerUseCase registerSuperManagerUseCase;

    @Operation(summary = "super 권한을 모두 가진 manager를 생성합니다. 테스트용입니다.")
    @PostMapping
    public Long registerSuperManager(@RequestBody RequestManager requestManager) {
        return registerSuperManagerUseCase.execute(requestManager);
    }
}
