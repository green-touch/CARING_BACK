package com.caring.manager_service.infra.user.client;

import com.caring.manager_service.common.external.user.dto.ResponseUserUuid;
import com.caring.manager_service.infra.user.vo.RequestUserWithShelterUuid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service")
public interface UserServiceClient {

    @PostMapping("/internal/users/register")
    ResponseUserUuid registerUser(@RequestBody RequestUserWithShelterUuid request);
}
