package com.caring.user_service.domain.user.business.adaptor;

import com.caring.user_service.common.annotation.Adaptor;
import com.caring.user_service.domain.user.entity.EmergencyContact;
import com.caring.user_service.domain.user.repository.EmergencyContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Adaptor
@RequiredArgsConstructor
public class EmergencyContactAdaptorImpl implements EmergencyContactAdaptor {

    private final EmergencyContactRepository emergencyContactRepository;

    @Override
    public EmergencyContact queryEmergencyContactByContactUuid(String contactUuid) {
        return emergencyContactRepository.findByContactUuid(contactUuid)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Contact not found with contact UUID: " + contactUuid));
    }
}
