package com.caring.user_service.domain.user.business.domainService;

import com.caring.user_service.common.annotation.DomainService;
import com.caring.user_service.domain.user.entity.EmergencyContact;
import com.caring.user_service.domain.user.entity.User;
import com.caring.user_service.domain.user.repository.EmergencyContactRepository;
import com.caring.user_service.presentation.dto.EmergencyContactDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@DomainService
@RequiredArgsConstructor
public class EmergencyContactDomainServiceImpl implements EmergencyContactDomainService {

    private final EmergencyContactRepository emergencyContactRepository;

    @Override
    public void addEmergencyContact(User user, EmergencyContactDTO dto) {
        log.info("{}, {}, {}",
                dto.getContactName(),
                dto.getContactPhoneNumber(),
                dto.getContactRelationship());
        EmergencyContact contact = EmergencyContact.builder()
                .user(user)
                .contactName(dto.getContactName())
                .contactRelationship(dto.getContactRelationship())
                .contactPhoneNumber(dto.getContactPhoneNumber())
                .contactUuid(UUID.randomUUID().toString())
                .build();

        emergencyContactRepository.save(contact);
    }

    @Override
    public void updateEmergencyContact(EmergencyContact emergencyContact, EmergencyContactDTO dto) {
        
        //TODO: emergency Validator 작업
        emergencyContact.changeName(dto.getContactName());
        emergencyContact.changeRelationship(dto.getContactRelationship());
        emergencyContact.changePhoneNumber(dto.getContactPhoneNumber());
    }

    @Override
    public void deleteEmergencyContact(EmergencyContact emergencyContact) {
        emergencyContactRepository.delete(emergencyContact);
    }
}
