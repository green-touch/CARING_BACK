package com.caring.manager_service.infra.user.client;

import com.caring.manager_service.infra.user.vo.RequestEmergencyContact;
import com.caring.manager_service.infra.user.vo.request.*;
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

    @DeleteMapping("/emergency-contacts/{contactUuid}")
    void deleteEmergencyContact(@PathVariable String contactUuid);

    @PatchMapping("/emergency-contacts/{contactUuid}")
    void updateEmergencyContact(
            @PathVariable String contactUuid,
            @RequestBody RequestEmergencyContactWithContactUuid request);

    @PatchMapping("/{userUuid}/phone-number")
    void updateUserPhoneNumber(@PathVariable String userUuid,
                               @RequestBody RequestPhoneNumber request);

    @PatchMapping("/{userUuid}/address")
    void updateUserAddress(@PathVariable String userUuid,
                           @RequestBody RequestAddress request);

    @PatchMapping("/{userUuid}/memo")
    void updateUserMemo(@RequestParam String userUuid,
                        @RequestBody RequestMemo request);

}
