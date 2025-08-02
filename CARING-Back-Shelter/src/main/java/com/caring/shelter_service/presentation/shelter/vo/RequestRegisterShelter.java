package com.caring.shelter_service.presentation.shelter.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RequestRegisterShelter {
    private final String name;

    private final String roadAddress;
    private final String detailAddress;
    private final String postalCode;

    private final String phoneNumber;
    private final String imageUrl;

}
