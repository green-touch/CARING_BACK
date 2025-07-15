package com.caring.manager_service.presentation.manager.vo.response;

import com.caring.manager_service.domain.manager.entity.Manager;
import com.caring.manager_service.presentation.manager.mapper.ManagerMapper;
import com.caring.manager_service.presentation.manager.vo.response.ResponseAuthority;
import com.caring.manager_service.presentation.manager.vo.response.ResponseManager;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class ResponseSpecificManager {
    private final ResponseManager responseManager;
    private final ResponseAuthority responseAuthority;
    private final String email;
    private final String phoneNumber;


    public static ResponseSpecificManager of(Manager manager) {
        return ResponseSpecificManager.builder()
                .responseManager(ManagerMapper.INSTANCE.toResponseManager(manager))
                .responseAuthority(ResponseAuthority.of(manager))
                .email(manager.getEmail())
                .phoneNumber(manager.getPhoneNumber())
                .build();
    }
}
