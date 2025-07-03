package com.caring.user_service.domain.user.business.validator;

import com.caring.user_service.common.annotation.Validator;
import com.caring.user_service.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

@Validator
@RequiredArgsConstructor
public class UserValidatorImpl implements UserValidator{
    private final PasswordEncoder passwordEncoder;

    /**
     * only use in filter, so need to throw filterException
     * @param user
     * @param password
     */
    @Override
    public void checkPasswordEncode(User user, String password) {
        if(!passwordEncoder.matches(password, user.getPassword()))
            throw new IllegalArgumentException("not match password");
    }

    @Override
    public void validateName(String name) {
        if(name == null) {
            throw new IllegalArgumentException("이름은 null일 수 없습니다");
        }
        if(!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("이름은 빈 문자열일 수 없습니다");
        }
    }

    @Override
    public void validatePassword(String password) {
        if(password == null) {
            throw new IllegalArgumentException("비밀번호는 null일 수 없습니다");
        }
        if(!StringUtils.hasText(password)) {
            throw new IllegalArgumentException("비밀번호는 빈 문자열일 수 없습니다");
        }
    }

    @Override
    public void validateMemberCode(String memberCode) {
        if(memberCode == null) {
            throw new IllegalArgumentException("멤버코드는 null일 수 없습니다");
        }
        if(!StringUtils.hasText(memberCode)) {
            throw new IllegalArgumentException("멤버코드는 빈 문자열일 수 없습니다");
        }
    }
}
