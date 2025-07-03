package com.caring.manager_service.presentation.manager.vo.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class ResponseSubmission {
    private final String submissionUuid;
    private final String name;
    private final String shelterUuid;
}
