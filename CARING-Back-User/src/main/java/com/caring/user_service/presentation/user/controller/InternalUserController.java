package com.caring.user_service.presentation.user.controller;

import com.caring.user_service.presentation.user.service.GetUserShelterUuidUseCase;
import com.caring.user_service.presentation.user.service.GetUsersByUuidListUseCase;
import com.caring.user_service.presentation.user.service.RegisterUserByManagerUseCase;
import com.caring.user_service.presentation.user.vo.RequestUserWithShelterUuid;
import com.caring.user_service.presentation.user.vo.ResponseUser;
import com.caring.user_service.presentation.user.vo.ResponseUserShelterUuid;
import com.caring.user_service.presentation.user.vo.ResponseUserUuid;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Hidden
@RestController
@RequestMapping("/internal/users")
@RequiredArgsConstructor
public class InternalUserController {

    private final RegisterUserByManagerUseCase registerUserByManagerUseCase;
    private final GetUserShelterUuidUseCase getUserShelterUuidUseCase;
    private final GetUsersByUuidListUseCase getUsersByUuidListUseCase;

    @PostMapping("/register")
    public ResponseUserUuid registerUser(@RequestBody RequestUserWithShelterUuid request) {
        return registerUserByManagerUseCase.execute(request);
    }

    @GetMapping("/{userUuid}/shelterUuid")
    public ResponseUserShelterUuid getUserShelterUuid(@PathVariable String userUuid) {
        return getUserShelterUuidUseCase.execute(userUuid);
    }

    @GetMapping
    public List<ResponseUser> queryUserByUuidList(@RequestBody List<String> uuidList) {
        return getUsersByUuidListUseCase.execute(uuidList);
    }
}
