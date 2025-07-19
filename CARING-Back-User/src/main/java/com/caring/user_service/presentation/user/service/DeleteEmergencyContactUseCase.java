package com.caring.user_service.presentation.user.service;

import com.caring.user_service.common.annotation.UseCase;
import com.caring.user_service.domain.user.business.adaptor.EmergencyContactAdaptor;
import com.caring.user_service.domain.user.business.domainservice.EmergencyContactDomainService;
import com.caring.user_service.domain.user.entity.EmergencyContact;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class DeleteEmergencyContactUseCase {

    private final EmergencyContactAdaptor emergencyContactAdaptor;
    private final EmergencyContactDomainService emergencyContactDomainService;

    public void execute(String contactUuid) {
        EmergencyContact emergencyContact = emergencyContactAdaptor.queryEmergencyContactByContactUuid(contactUuid);
        emergencyContactDomainService.deleteEmergencyContact(emergencyContact);
    }
}
