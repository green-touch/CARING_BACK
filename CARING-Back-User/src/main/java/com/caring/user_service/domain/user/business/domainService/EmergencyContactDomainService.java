package com.caring.user_service.domain.user.business.domainService;

import com.caring.user_service.domain.user.entity.EmergencyContact;
import com.caring.user_service.domain.user.entity.User;
import com.caring.user_service.presentation.dto.EmergencyContactDTO;

public interface EmergencyContactDomainService {

    void addEmergencyContact(User user, EmergencyContactDTO emergencyContactDTO);

    void updateEmergencyContact(EmergencyContact emergencyContact, EmergencyContactDTO emergencyContactDTO);

    void deleteEmergencyContact(EmergencyContact emergencyContact);
}
