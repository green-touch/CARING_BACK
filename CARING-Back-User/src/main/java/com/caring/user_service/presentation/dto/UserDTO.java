package com.caring.user_service.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {
    private String name;
    private String password;
    private LocalDate birthDate;
    private String phoneNumber;
    private String roadAddress;
    private String detailAddress;
}
