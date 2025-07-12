package com.caring.user_service.presentation.user.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ResponseUserHomeInfo {
    private final String name;
    private final String userUuid;
    private final String shelterUuid;
    private final String profileImageUrl;
}
