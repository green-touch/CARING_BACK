package com.caring.manager_service.presentation.manager.service;

import com.caring.manager_service.common.annotation.UseCase;
import com.caring.manager_service.domain.authority.business.adaptor.AuthorityAdaptor;
import com.caring.manager_service.domain.authority.entity.ManagerRole;
import com.caring.manager_service.domain.manager.business.adaptor.ManagerAdaptor;
import com.caring.manager_service.domain.manager.business.service.ManagerDomainService;
import com.caring.manager_service.domain.manager.business.validator.ManagerValidator;
import com.caring.manager_service.domain.manager.entity.Manager;
import com.caring.manager_service.domain.manager.entity.Submission;
import com.caring.manager_service.domain.shelter.business.service.ShelterDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class PermissionRegisteringManagerUseCase {

    private final ManagerAdaptor managerAdaptor;
    private final ManagerValidator managerValidator;
    private final ManagerDomainService managerDomainService;
    private final ShelterDomainService shelterDomainService;
    private final AuthorityAdaptor authorityAdaptor;

    public Long execute(String submissionUuid, String memberCode) {
        // check authorization
        Manager manager = managerAdaptor.queryByMemberCode(memberCode);
        if (!managerValidator.isSuper(manager)) {
            throw new RuntimeException("not authorization");
        }
        // register manager
        Submission submission = managerAdaptor.querySubmissionByUuid(submissionUuid);
        Manager insertManager = managerDomainService.registerManager(
                submission.getName(),
                submission.getPassword(),
                authorityAdaptor.queryByManagerRole(ManagerRole.MANAGE)
        );
        shelterDomainService.addShelterStaff(submission.getShelterUuid(), insertManager);
        // remove submission
        managerDomainService.removeSubmission(submissionUuid);
        return insertManager.getId();
    }
}
