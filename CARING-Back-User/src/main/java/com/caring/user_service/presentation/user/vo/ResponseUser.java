package com.caring.user_service.presentation.user.vo;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class ResponseUser {
    private final String name;
    private final String userUuid;
    private final String memberCode;
}
