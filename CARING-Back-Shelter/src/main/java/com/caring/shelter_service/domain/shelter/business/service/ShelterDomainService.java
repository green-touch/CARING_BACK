package com.caring.shelter_service.domain.shelter.business.service;

import com.caring.shelter_service.domain.shelter.entity.Shelter;
import com.caring.shelter_service.presentation.shelter.vo.RequestRegisterShelter;

public interface ShelterDomainService {

    Shelter registerShelter(RequestRegisterShelter requestRegisterShelter);
}
