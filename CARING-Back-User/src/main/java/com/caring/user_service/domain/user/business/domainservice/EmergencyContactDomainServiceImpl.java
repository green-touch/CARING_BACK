package com.caring.user_service.domain.user.business.domainservice;

import com.caring.user_service.common.annotation.DomainService;
import com.caring.user_service.domain.user.entity.EmergencyContact;
import com.caring.user_service.domain.user.entity.User;
import com.caring.user_service.domain.user.repository.EmergencyContactRepository;
import com.caring.user_service.presentation.dto.EmergencyContactDTO;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@DomainService
@RequiredArgsConstructor
public class EmergencyContactDomainServiceImpl implements EmergencyContactDomainService {

    private final EmergencyContactRepository emergencyContactRepository;

    @Override
    public void addEmergencyContact(User user, EmergencyContactDTO dto) {
        EmergencyContact contact = EmergencyContact.builder()
                .user(user)
                .contactName(dto.getName())
                .contactRelationship(dto.getRelationship())
                .contactPhoneNumber(dto.getPhoneNumber())
                .contactUuid(UUID.randomUUID().toString())
                .build();

        emergencyContactRepository.save(contact);
    }

    @Override
    public void updateEmergencyContact(EmergencyContact emergencyContact, EmergencyContactDTO dto) {
        
        //TODO: emergency Validator 작업
        emergencyContact.changeName(dto.getName());
        emergencyContact.changeRelationship(dto.getRelationship());
        emergencyContact.changePhoneNumber(dto.getPhoneNumber());
    }

    @Override
    public void deleteEmergencyContact(EmergencyContact emergencyContact) {
        emergencyContactRepository.delete(emergencyContact);
    }
}
