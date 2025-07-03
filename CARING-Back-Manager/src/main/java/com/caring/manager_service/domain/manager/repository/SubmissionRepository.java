package com.caring.manager_service.domain.manager.repository;

import com.caring.manager_service.domain.manager.entity.Submission;
import com.caring.manager_service.domain.manager.entity.SubmissionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findByStatus(SubmissionStatus status);

    Optional<Submission> findBySubmissionUuid(String submissionUuid);

    void deleteBySubmissionUuid(String submissionUuid);
}
