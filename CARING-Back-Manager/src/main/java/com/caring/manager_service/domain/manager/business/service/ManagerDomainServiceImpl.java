package com.caring.manager_service.domain.manager.business.service;

import com.caring.manager_service.common.annotation.DomainService;
import com.caring.manager_service.domain.manager.business.validator.ManagerValidator;
import com.caring.manager_service.domain.manager.entity.Manager;
import com.caring.manager_service.domain.manager.entity.ManagerGroup;
import com.caring.manager_service.domain.manager.repository.ManagerGroupRepository;
import com.caring.manager_service.domain.manager.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static com.caring.manager_service.common.consts.StaticVariable.MANAGER_MEMBER_CODE_PRESET;
import static com.caring.manager_service.common.util.RandomNumberUtil.generateRandomMemberCode;

@DomainService
@RequiredArgsConstructor
public class ManagerDomainServiceImpl implements ManagerDomainService{

    private final ManagerRepository managerRepository;
    private final PasswordEncoder passwordEncoder;
    private final ManagerGroupRepository managerGroupRepository;
    private final ManagerValidator managerValidator;

    @Override
    public Manager registerManager(String name, String password) {

        managerValidator.checkName(name);
        managerValidator.checkPassword(password);

        Manager newManager = Manager.builder()
                .managerUuid(UUID.randomUUID().toString())
                .memberCode(generateRandomMemberCode(MANAGER_MEMBER_CODE_PRESET))
                .name(name)
                .password(passwordEncoder.encode(password))
                .build();


        return managerRepository.save(newManager);
    }

    @Override
    public ManagerGroup addUserToManagerGroup(Manager manager, String userUuid) {
        ManagerGroup newManagerGroup = ManagerGroup.builder()
                .manager(manager)
                .userUuid(userUuid)
                .build();
        return managerGroupRepository.save(newManagerGroup);
    }
}
