package com.caring.manager_service.domain.manager.business.validator;

import com.caring.manager_service.common.annotation.Validator;
import com.caring.manager_service.domain.manager.entity.Manager;
import com.caring.manager_service.domain.manager.repository.ManagerGroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

@Slf4j
@Validator
@RequiredArgsConstructor
public class ManagerValidatorImpl implements ManagerValidator {

    private final PasswordEncoder passwordEncoder;
    private final ManagerGroupRepository managerGroupRepository;

    /**
     * only use in filter, so need to throw filterException
     * @param manager
     * @param password
     */
    @Override
    public void checkPasswordEncode(Manager manager, String password) {
        if(!passwordEncoder.matches(password, manager.getPassword()))
            throw new IllegalArgumentException("not match password");
    }

    @Override
    public void checkName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("이름은 null일 수 없습니다");
        }
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("이름은 빈 문자열일 수 없습니다");
        }
    }

    @Override
    public void checkPassword(String password) {
        if (password == null) {
            throw new IllegalArgumentException("비밀번호는 null일 수 없습니다");
        }
        if (!StringUtils.hasText(password)) {
            throw new IllegalArgumentException("비밀번호는 빈 문자열일 수 없습니다");
        }
    }

    @Override
    public boolean isGroupedUser(String managerCode, String userUuid) {
        return managerGroupRepository.existsByManagerMemberCodeAndUserUuid(managerCode, userUuid);
    }
}
