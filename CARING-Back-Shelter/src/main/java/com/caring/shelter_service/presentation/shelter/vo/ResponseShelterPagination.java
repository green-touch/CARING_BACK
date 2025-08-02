package com.caring.shelter_service.presentation.shelter.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ResponseShelterPagination {
    @Builder.Default
    private final List<ResponseShelterInfo> responseShelterInfoList = new ArrayList<>();
    private final int page;
    private final int totalPages;
}
