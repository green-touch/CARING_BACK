package com.caring.shelter_service.presentation.shelter.controller;

import com.caring.shelter_service.domain.shelter.business.adaptor.ShelterAdaptor;
import com.caring.shelter_service.domain.shelter.business.service.ShelterDomainService;
import com.caring.shelter_service.domain.shelter.entity.Shelter;
import com.caring.shelter_service.presentation.shelter.service.GetAllShelterUseCase;
import com.caring.shelter_service.presentation.shelter.vo.RequestRegisterShelter;
import com.caring.shelter_service.presentation.shelter.vo.ResponseShelterInfo;
import com.caring.shelter_service.presentation.shelter.vo.ResponseShelterPagination;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "[보호소(ACCESS)]")
@RestController
@RequestMapping("/v1/api/access/shelters")
@RequiredArgsConstructor
public class ShelterAccessApiController {

    private final ShelterDomainService shelterDomainService;
    private final GetAllShelterUseCase getAllShelterUseCase;

    @Operation(summary = "test for shelter service")
    @GetMapping("/test")
    public ResponseEntity<Boolean> test() {
        return ResponseEntity.ok(true);
    }

    @Operation(summary = "보호소 정보를 생성합니다.")
    @PostMapping
    public ResponseEntity<Long> registerShelterInformation(@RequestBody RequestRegisterShelter requestRegisterShelter) {
        return ResponseEntity.ok(shelterDomainService.registerShelter(requestRegisterShelter).getId());
    }

    @Operation(summary = "보호소 정보를 목록으로 불러옵니다.")
    @GetMapping
    public ResponseEntity<ResponseShelterPagination> getAllShelter(@RequestParam(defaultValue = "0") int currentPage,
                                                                   @RequestParam int pageSize) {
        return ResponseEntity.ok(getAllShelterUseCase.execute(currentPage, pageSize));
    }
}
