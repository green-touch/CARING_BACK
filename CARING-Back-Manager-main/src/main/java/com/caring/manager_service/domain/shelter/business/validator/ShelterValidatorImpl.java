package com.caring.manager_service.domain.shelter.business.validator;

import com.caring.manager_service.common.annotation.Validator;
import com.caring.manager_service.domain.manager.entity.Manager;
import com.caring.manager_service.domain.shelter.repository.ShelterRepository;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@Validator
@RequiredArgsConstructor
public class ShelterValidatorImpl implements ShelterValidator{

    private final ShelterRepository shelterRepository;

    @Override
    public boolean isSameShelterUserAndManager(String userShelterUuid, Manager manager) {
        return Objects.equals(userShelterUuid, manager.getShelterUuid());
    }

    @Override
    public boolean isExistShelter(String shelterUuid) {
        return shelterRepository.existsByShelterUuid(shelterUuid);
    }
}
