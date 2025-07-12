package com.caring.user_service.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {
    private String name;
    private String password;
    private String birthDate; //ex: 001027
    private String phoneNumber;
    private String roadAddress;
    private String detailAddress;
}
