package com.caring.manager_service.domain.manager.business.adaptor;

import com.caring.manager_service.common.annotation.Adaptor;
import com.caring.manager_service.domain.manager.entity.Manager;
import com.caring.manager_service.domain.manager.entity.Submission;
import com.caring.manager_service.domain.manager.entity.SubmissionStatus;
import com.caring.manager_service.domain.manager.repository.ManagerGroupRepository;
import com.caring.manager_service.domain.manager.repository.ManagerRepository;
import com.caring.manager_service.domain.manager.repository.SubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Adaptor
@RequiredArgsConstructor
public class ManagerAdaptorImpl implements ManagerAdaptor {

    private final ManagerRepository managerRepository;
    private final SubmissionRepository submissionRepository;
    private final ManagerGroupRepository managerGroupRepository;

    @Override
    public Manager queryByMemberCode(String memberCode) {
        return managerRepository.findByMemberCode(memberCode)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Manager not found with memberCode: " + memberCode));
    }

    @Override
    public Manager queryByManagerUuid(String managerUuid) {
        return managerRepository.findByManagerUuid(managerUuid)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Manager not found with UUID: " + managerUuid));
    }

    @Override
    public List<Manager> queryByShelter(String shelterUuid) {
        return managerRepository.findByShelterUuid(shelterUuid);
    }

    @Override
    public Submission querySubmissionByUuid(String submissionUuid) {
        return submissionRepository.findBySubmissionUuid(submissionUuid)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Submission not found with UUID: " + submissionUuid));
    }

    @Override
    public List<Submission> querySubmissionsByStatus(SubmissionStatus status) {
        return submissionRepository.findByStatus(status);
    }

    @Override
    public List<Manager> queryAll() {
        return managerRepository.findAll();
    }

    @Override
    public List<String> queryUserUuidsByManager(Manager manager) {
        return managerGroupRepository.findUsersByManager(manager);
    }
}
