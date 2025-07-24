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

/**
 * Patch 사용 불가능
 */
@FeignClient(name = "user-service")
public interface UserServiceClient {

    @PostMapping("/internal/users/register")
    ResponseUserUuid registerUser(@RequestBody RequestUserWithShelterUuid request);

    @GetMapping("/internal/users")
    List<ResponseUser> queryUserByUuidList(@RequestParam List<String> uuidList);

    @PostMapping("/internal/users/register")
    String registerUser(@RequestBody RequestUser requestUser);

    @GetMapping("/internal/users/info/{userUuid}")
    ResponseUserDetailInfo getUserDetailInfo(@RequestParam String userUuid);

    @PostMapping("/internal/users/{userUuid}/emergency-contacts")
    void saveEmergencyContact(@PathVariable String userUuid, @RequestBody RequestEmergencyContact request);

    @DeleteMapping("/internal/users/emergency-contacts/{contactUuid}")
    void deleteEmergencyContact(@PathVariable String contactUuid);

    @PutMapping("/internal/users/emergency-contacts/{contactUuid}")
    void updateEmergencyContact(
            @PathVariable String contactUuid,
            @RequestBody RequestEmergencyContactWithContactUuid request);

    @PutMapping("/internal/users/{userUuid}/phone-number")
    void updateUserPhoneNumber(@PathVariable String userUuid,
                               @RequestBody RequestPhoneNumber request);

    @PutMapping("/internal/users/{userUuid}/address")
    void updateUserAddress(@PathVariable String userUuid,
                           @RequestBody RequestAddress request);

    @PutMapping("/internal/users/{userUuid}/memo")
    void updateUserMemo(@RequestParam String userUuid,
                        @RequestBody RequestMemo request);

}
