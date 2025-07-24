package com.caring.user_service.presentation.user.service;

import com.caring.user_service.common.annotation.UseCase;
import com.caring.user_service.domain.user.business.adaptor.EmergencyContactAdaptor;
import com.caring.user_service.domain.user.business.domainservice.EmergencyContactDomainService;
import com.caring.user_service.domain.user.entity.EmergencyContact;
import com.caring.user_service.presentation.user.mapper.EmergencyContactMapper;
import com.caring.user_service.presentation.user.vo.RequestEmergencyContactWithContactUuid;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class UpdateEmergencyContactUseCase {

    private final EmergencyContactAdaptor emergencyContactAdaptor;
    private final EmergencyContactDomainService emergencyContactDomainService;
    private final EmergencyContactMapper emergencyContactMapper;

    public void execute(String contactUuid, RequestEmergencyContactWithContactUuid request) {
        EmergencyContact emergencyContact = emergencyContactAdaptor
                .queryEmergencyContactByContactUuid(contactUuid);

        emergencyContactDomainService.updateEmergencyContact(
                emergencyContact, emergencyContactMapper.toEmergencyContactDTO(request));
    }
}
