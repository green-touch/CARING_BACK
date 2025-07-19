package com.caring.user_service.domain.user.business.domainservice;

import com.caring.user_service.common.annotation.DomainService;
import com.caring.user_service.domain.user.entity.EmergencyContact;
import com.caring.user_service.domain.user.entity.User;
import com.caring.user_service.domain.user.repository.EmergencyContactRepository;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@DomainService
@RequiredArgsConstructor
public class EmergencyContactDomainServiceImpl implements EmergencyContactDomainService {

    private final EmergencyContactRepository emergencyContactRepository;

    @Override
    public void addEmergencyContact(User user, String name, String relationship, String phoneNumber) {
        EmergencyContact contact = EmergencyContact.builder()
                .user(user)
                .contactName(name)
                .contactRelationship(relationship)
                .contactPhoneNumber(phoneNumber)
                .contactUuid(UUID.randomUUID().toString())
                .build();

        emergencyContactRepository.save(contact);
    }

    @Override
    public void updateEmergencyContact(EmergencyContact emergencyContact, String name, String relationship, String phoneNumber) {
        
        //TODO: emergency Validator 작업
        emergencyContact.changeName(name);
        emergencyContact.changeRelationship(relationship);
        emergencyContact.changePhoneNumber(phoneNumber);
    }

    @Override
    public void deleteEmergencyContact(EmergencyContact emergencyContact) {
        emergencyContactRepository.delete(emergencyContact);
    }
}
