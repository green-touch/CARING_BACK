package com.caring.shelter_service.presentation.shelter.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "[보호소(ACCESS)]")
@RestController
@RequestMapping("/v1/api/access/shelters")
@RequiredArgsConstructor
public class ShelterAccessApiController {

    @Operation(summary = "test for shelter service")
    @GetMapping("/test")
    public ResponseEntity<Boolean> test() {
        return ResponseEntity.ok(true);
    }
}
