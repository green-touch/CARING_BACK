package com.caring.user_service.domain.user.business.adaptor;

import com.caring.user_service.domain.user.entity.User;

import java.time.LocalDate;
import java.util.List;

public interface UserAdaptor {

    User queryUserById(Long userId);

    List<User> queryAll();

    User queryUserByMemberCode(String memberCode);

    User queryUserByUserUuid(String userUuid);

    List<User> queryByUserUuidList(List<String> userUuidList);

    User queryUserByNameAndBirthDateAndPhoneNumber(String name, LocalDate birthDate, String phoneNumber);
}
