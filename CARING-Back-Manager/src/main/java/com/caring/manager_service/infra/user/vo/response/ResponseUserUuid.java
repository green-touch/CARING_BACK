package com.caring.manager_service.infra.user.vo.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ResponseUserUuid {
    private String userUuid;
    private String userId;

}
