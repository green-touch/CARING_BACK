package com.caring.shelter_service.presentation.shelter.controller;

import com.caring.shelter_service.presentation.shelter.service.RegisterShelterInformationUseCase;
import com.caring.shelter_service.presentation.shelter.vo.RequestRegisterShelter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "[보호소(ACCESS)]")
@RestController
@RequestMapping("/v1/api/access/shelters")
@RequiredArgsConstructor
public class ShelterAccessApiController {

    private RegisterShelterInformationUseCase registerShelterInformationUseCase;

    @Operation(summary = "test for shelter service")
    @GetMapping("/test")
    public ResponseEntity<Boolean> test() {
        return ResponseEntity.ok(true);
    }

    @Operation(summary = "보호소 정보를 생성합니다.")
    @PostMapping
    public ResponseEntity<Long> registerShelterInformation(@RequestBody RequestRegisterShelter requestRegisterShelter) {
        return ResponseEntity.ok(registerShelterInformationUseCase.execute(requestRegisterShelter));
    }
}
