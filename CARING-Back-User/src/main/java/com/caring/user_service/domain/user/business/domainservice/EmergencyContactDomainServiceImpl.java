package com.caring.user_service.domain.user.business.domainservice;

import com.caring.user_service.common.annotation.DomainService;
import com.caring.user_service.domain.user.entity.EmergencyContact;
import com.caring.user_service.domain.user.entity.User;
import com.caring.user_service.domain.user.repository.EmergencyContactRepository;
import lombok.RequiredArgsConstructor;

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
                .build();

        emergencyContactRepository.save(contact);
    }
}
