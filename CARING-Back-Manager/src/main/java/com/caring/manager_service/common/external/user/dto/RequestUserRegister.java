package com.caring.manager_service.common.external.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestUserRegister {
    private String name;
    private String password;
    private String shelterUuid;
}
