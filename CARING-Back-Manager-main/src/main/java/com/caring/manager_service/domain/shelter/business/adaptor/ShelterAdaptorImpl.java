package com.caring.manager_service.domain.shelter.business.adaptor;

import com.caring.manager_service.common.annotation.Adaptor;
import com.caring.manager_service.domain.shelter.entity.Shelter;
import com.caring.manager_service.domain.shelter.repository.ShelterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Adaptor
@RequiredArgsConstructor
public class ShelterAdaptorImpl implements ShelterAdaptor {

    private final ShelterRepository shelterRepository;

    @Override
    public Shelter queryByShelterUuid(String shelterUuid) {
        return shelterRepository.findByShelterUuid(shelterUuid)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Shelter not found with UUID: " + shelterUuid));
    }
}
