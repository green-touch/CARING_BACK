package com.caring.shelter_service.domain.shelter.business.service;

import com.caring.shelter_service.common.annotation.DomainService;
import com.caring.shelter_service.domain.shelter.entity.Shelter;
import com.caring.shelter_service.domain.shelter.repository.ShelterRepository;
import com.caring.shelter_service.presentation.shelter.vo.RequestRegisterShelter;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class ShelterDomainServiceImpl implements ShelterDomainService{

    private final ShelterRepository shelterRepository;
    @Override
    public Shelter registerShelter(RequestRegisterShelter requestRegisterShelter) {
        Shelter shelter = Shelter.builder()
                .name(requestRegisterShelter.getName())
                .detailAddress(requestRegisterShelter.getDetailAddress())
                .roadAddress(requestRegisterShelter.getRoadAddress())
                .postalCode(requestRegisterShelter.getPostalCode())
                .phoneNumber(requestRegisterShelter.getPhoneNumber())
                .imageUrl(requestRegisterShelter.getImageUrl())
                .build();
        return shelterRepository.save(shelter);
    }
}
