package com.caring.manager_service.presentation.shelter.controller;

import com.caring.manager_service.common.annotation.ManagerCode;
import com.caring.manager_service.presentation.shelter.service.RegisterShelterUseCase;
import com.caring.manager_service.presentation.shelter.vo.RequestShelter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "[보호소(AUTH)]")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/shelters")
public class ShelterApiController {

    private final RegisterShelterUseCase registerShelterUseCase;

    @Operation(summary = "보호소를 등록합니다. 이때 등록자(매니저)의 권한은 SUPER가 존재해야 합니다.")
    @PostMapping
    //TODO use Roles annotation
    public Long registerShelter(@ManagerCode String memberCode,
                                @RequestBody RequestShelter requestShelter) {
        return registerShelterUseCase.execute(requestShelter, memberCode);
    }
}
