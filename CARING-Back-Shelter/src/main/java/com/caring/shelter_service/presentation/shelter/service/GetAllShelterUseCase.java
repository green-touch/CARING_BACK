package com.caring.shelter_service.presentation.shelter.service;

import com.caring.shelter_service.common.annotation.UseCase;
import com.caring.shelter_service.domain.shelter.business.adaptor.ShelterAdaptor;
import com.caring.shelter_service.domain.shelter.entity.Shelter;
import com.caring.shelter_service.presentation.shelter.vo.ResponseShelterInfo;
import com.caring.shelter_service.presentation.shelter.vo.ResponseShelterPagination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@UseCase
@RequiredArgsConstructor
public class GetAllShelterUseCase {

    private final ShelterAdaptor shelterAdaptor;

    public ResponseShelterPagination execute(int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        Page<Shelter> shelterPage = shelterAdaptor.getShelterList(pageable);
        List<ResponseShelterInfo> collect = shelterPage.getContent().stream()
                .map(shelter -> ResponseShelterInfo.builder()
                        .shelterId(shelter.getId())
                        .name(shelter.getName())
                        .detailAddress(shelter.getDetailAddress())
                        .roadAddress(shelter.getRoadAddress())
                        .phoneNumber(shelter.getPhoneNumber())
                        .postalCode(shelter.getPostalCode())
                        .imageUrl(shelter.getImageUrl())
                        .build())
                .collect(Collectors.toList());

        return ResponseShelterPagination.builder()
                .responseShelterInfoList(collect)
                .totalPages(shelterPage.getTotalPages())
                .page(shelterPage.getNumber())
                .build();
    }
}
