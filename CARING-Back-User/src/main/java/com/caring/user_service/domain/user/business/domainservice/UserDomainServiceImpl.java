package com.caring.user_service.domain.user.business.domainservice;

import com.caring.user_service.common.annotation.DomainService;
import com.caring.user_service.domain.user.business.validator.UserValidator;
import com.caring.user_service.domain.user.entity.Role;
import com.caring.user_service.domain.user.entity.User;
import com.caring.user_service.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static com.caring.user_service.common.consts.StaticVariable.USER_MEMBER_CODE_PRESET;
import static com.caring.user_service.common.util.RandomNumberUtil.generateRandomMemberCode;

@DomainService
@RequiredArgsConstructor
public class UserDomainServiceImpl implements UserDomainService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserValidator userValidator;

    @Override
    public User registerUser(String name, String password) {
        userValidator.validateName(name);
        userValidator.validatePassword(password);

        User newUser = User.builder()
                .memberCode(generateRandomMemberCode(USER_MEMBER_CODE_PRESET))
                .userUuid(UUID.randomUUID().toString())
                .role(Role.USER)
                .password(passwordEncoder.encode(password))
                .name(name)
                .build();
        return userRepository.save(newUser);
    }

    @Override
    public User registerUserWithShelterUuid(String name, String password, String shelterUuid) {
        userValidator.validateName(name);
        userValidator.validatePassword(password);

        User newUser = User.builder()
                .memberCode(generateRandomMemberCode(USER_MEMBER_CODE_PRESET))
                .userUuid(UUID.randomUUID().toString())
                .role(Role.USER)
                .password(passwordEncoder.encode(password))
                .name(name)
                .shelterUuid(shelterUuid)
                .build();
        return userRepository.save(newUser);
    }

    @Override
    public void resetPassword(User user, String encodedPassword) {
        if (user.isSamePassword(encodedPassword)) {
            throw new IllegalArgumentException("이전에 사용한 비밀번호와 동일합니다.");
        }
        user.changePassword(encodedPassword);
    }
}
