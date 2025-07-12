package com.caring.user_service.domain.user.business.domainservice;

import com.caring.user_service.domain.user.entity.User;

public interface EmergencyContactDomainService {

    void addEmergencyContact(User user, String name, String relationship, String phoneNumber);
}
