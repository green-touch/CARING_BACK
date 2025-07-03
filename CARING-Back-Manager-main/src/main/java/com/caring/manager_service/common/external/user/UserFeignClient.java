package com.caring.manager_service.common.external.user;

import com.caring.manager_service.common.external.user.dto.RequestUserRegister;
import com.caring.manager_service.common.external.user.dto.ResponseUserShelterUuid;
import com.caring.manager_service.common.external.user.dto.ResponseUserUuid;
import com.caring.manager_service.presentation.manager.vo.response.ResponseUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "user-service", url = "${user-service.url:}")
public interface UserFeignClient {

    @GetMapping("/internal/users/{userUuid}/shelterUuid")
    ResponseUserShelterUuid getUserShelterUuid(@PathVariable("userUuid") String userUuid);

    @PostMapping("/internal/users/register")
    ResponseUserUuid registerUser(@RequestBody RequestUserRegister requestUserRegister);

    @PostMapping("/internal/users/byUuidList")
    List<ResponseUser> queryUsersByUuidList(@RequestBody List<String> userUuids);
}
