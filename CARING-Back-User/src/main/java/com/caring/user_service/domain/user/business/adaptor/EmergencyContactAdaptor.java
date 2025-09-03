package com.caring.user_service.domain.user.business.adaptor;

import com.caring.user_service.domain.user.entity.EmergencyContact;

public interface EmergencyContactAdaptor {

    EmergencyContact queryEmergencyContactByContactUuid(String contactUuid);
}
