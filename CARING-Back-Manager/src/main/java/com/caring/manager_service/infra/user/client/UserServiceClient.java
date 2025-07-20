package com.caring.manager_service.infra.user.client;

import com.caring.manager_service.infra.user.vo.request.RequestUser;
import com.caring.manager_service.infra.user.vo.request.RequestUserWithShelterUuid;
import com.caring.manager_service.infra.user.vo.response.ResponseUser;
import com.caring.manager_service.infra.user.vo.response.ResponseUserDetailInfo;
import com.caring.manager_service.infra.user.vo.response.ResponseUserUuid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "user-service")
public interface UserServiceClient {

    @PostMapping("/internal/users/register")
    ResponseUserUuid registerUser(@RequestBody RequestUserWithShelterUuid request);

    @GetMapping("/internal/users")
    List<ResponseUser> queryUserByUuidList(@RequestBody List<String> uuidList);

    @PostMapping("/v1/api/access/users/register")
    String registerUser(@RequestBody RequestUser requestUser);

    @GetMapping("/internal/users/info")
    ResponseUserDetailInfo getUserDetailInfo(@RequestParam String memberCode);
    //TODO 비상 연락망 수정

}
