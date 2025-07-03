package com.caring.manager_service.presentation.manager.vo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ResponseUser {
    private final String name;
    private final String userUuid;
    private final String memberCode;
}
