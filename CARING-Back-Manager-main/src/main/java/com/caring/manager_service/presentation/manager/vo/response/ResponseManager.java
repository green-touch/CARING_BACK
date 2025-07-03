package com.caring.manager_service.presentation.manager.vo.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class ResponseManager {

    private final String managerUuid;
    private final String name;
    private final String shelterUuid;

}
