package com.caring.manager_service.infra.user.client;

import com.caring.manager_service.infra.user.vo.RequestEmergencyContact;
import com.caring.manager_service.infra.user.vo.request.RequestUser;
import com.caring.manager_service.infra.user.vo.request.RequestUserWithShelterUuid;
import com.caring.manager_service.infra.user.vo.response.ResponseUser;
import com.caring.manager_service.infra.user.vo.response.ResponseUserDetailInfo;
import com.caring.manager_service.infra.user.vo.response.ResponseUserUuid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "user-service")
public interface UserServiceClient {

    @PostMapping("/internal/users/register")
    ResponseUserUuid registerUser(@RequestBody RequestUserWithShelterUuid request);

    @GetMapping("/internal/users")
    List<ResponseUser> queryUserByUuidList(@RequestBody List<String> uuidList);

    @PostMapping("/v1/api/access/users/register")
    String registerUser(@RequestBody RequestUser requestUser);

    @GetMapping("/internal/users/info/{userUuid}")
    ResponseUserDetailInfo getUserDetailInfo(@RequestParam String userUuid);

    @PostMapping("/internal/users/{userUuid}/emergency-contacts")
    void saveEmergencyContact(@PathVariable String userUuid, @RequestBody RequestEmergencyContact request);

}
