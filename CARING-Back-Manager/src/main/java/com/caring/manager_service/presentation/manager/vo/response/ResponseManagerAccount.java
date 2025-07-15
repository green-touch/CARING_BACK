package com.caring.manager_service.presentation.manager.vo.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class ResponseManagerAccount {
    private final String memberCode;
    private final String name;
    private final String managerUuid;
}
