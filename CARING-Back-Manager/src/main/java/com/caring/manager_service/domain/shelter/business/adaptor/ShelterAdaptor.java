package com.caring.manager_service.domain.shelter.business.adaptor;

import com.caring.manager_service.domain.shelter.entity.Shelter;

public interface ShelterAdaptor {

    Shelter queryByShelterUuid(String shelterUuid);
}
