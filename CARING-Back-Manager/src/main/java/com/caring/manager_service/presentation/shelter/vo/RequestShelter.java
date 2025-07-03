package com.caring.manager_service.presentation.shelter.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class RequestShelter {
    private final String location;
    private final String name;
}
