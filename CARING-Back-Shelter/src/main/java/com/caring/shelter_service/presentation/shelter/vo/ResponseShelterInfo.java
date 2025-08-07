package com.caring.shelter_service.presentation.shelter.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ResponseShelterInfo {
    private final Long shelterId;
    private final String name;

    private final String roadAddress;
    private final String detailAddress;
    private final String postalCode;

    private final String phoneNumber;
    private final String imageUrl;
}
