package com.caring.shelter_service.presentation.shelter.service;

import com.caring.shelter_service.common.annotation.UseCase;
import com.caring.shelter_service.presentation.shelter.vo.RequestRegisterShelter;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
public class RegisterShelterInformationUseCase {
    public Long execute(RequestRegisterShelter requestRegisterShelter) {
        return null;
    }
}
