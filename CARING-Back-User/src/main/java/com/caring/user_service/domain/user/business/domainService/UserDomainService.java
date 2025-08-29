package com.caring.user_service.domain.user.business.domainService;

import com.caring.user_service.domain.user.entity.User;
import com.caring.user_service.presentation.dto.AddressDTO;
import com.caring.user_service.presentation.dto.UserDTO;

public interface UserDomainService {

    User registerUser(String name, String password);

    User registerUser(UserDTO userDTO);

    User registerUserWithShelterUuid(String name, String password, String shelterUuid);

    void resetPassword(User user, String encodedPassword);

    void updatePhoneNumber(User user, String phoneNumber);

    void updateAddress(User user, AddressDTO addressDTO);

    void updateMemo(User user, String memo);
}
