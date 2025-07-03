package com.caring.manager_service.domain.manager.business.service;

import com.caring.manager_service.common.annotation.DomainService;
import com.caring.manager_service.domain.authority.entity.Authority;
import com.caring.manager_service.domain.authority.entity.ManagerAuthority;
import com.caring.manager_service.domain.manager.business.validator.ManagerValidator;
import com.caring.manager_service.domain.manager.entity.Manager;
import com.caring.manager_service.domain.manager.entity.ManagerGroup;
import com.caring.manager_service.domain.manager.entity.Submission;
import com.caring.manager_service.domain.manager.repository.ManagerGroupRepository;
import com.caring.manager_service.domain.manager.repository.ManagerRepository;
import com.caring.manager_service.domain.manager.repository.SubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static com.caring.manager_service.common.consts.StaticVariable.MANAGER_MEMBER_CODE_PRESET;
import static com.caring.manager_service.common.util.RandomNumberUtil.generateRandomMemberCode;
import static com.caring.manager_service.domain.manager.entity.SubmissionStatus.APPLY;

@DomainService
@RequiredArgsConstructor
public class ManagerDomainServiceImpl implements ManagerDomainService{

    private final ManagerRepository managerRepository;
    private final SubmissionRepository submissionRepository;
    private final PasswordEncoder passwordEncoder;
    private final ManagerGroupRepository managerGroupRepository;
    private final ManagerValidator managerValidator;

    @Override
    public Manager registerManager(String name, String password, Authority authority) {

        managerValidator.checkName(name);
        managerValidator.checkPassword(password);

        Manager newManager = Manager.builder()
                .managerUuid(UUID.randomUUID().toString())
                .memberCode(generateRandomMemberCode(MANAGER_MEMBER_CODE_PRESET))
                .name(name)
                .password(passwordEncoder.encode(password))
                .build();
        ManagerAuthority.builder()
                .manager(newManager)
                .authority(authority)
                .build()
                .link(newManager);

        return managerRepository.save(newManager);
    }

    @Override
    public Submission applyManager(String name, String password, String shelterUuid) {
        Submission application = Submission.builder()
                .submissionUuid(UUID.randomUUID().toString())
                .name(name)
                .password(password)
                .shelterUuid(shelterUuid)
                .status(APPLY)
                .build();

        return submissionRepository.save(application);
    }

    @Override
    public void removeSubmission(String submissionUuid) {
        submissionRepository.deleteBySubmissionUuid(submissionUuid);
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
