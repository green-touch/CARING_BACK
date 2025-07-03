package com.caring.manager_service.domain.shelter.business.validator;

import com.caring.manager_service.domain.manager.entity.Manager;

public interface ShelterValidator {

    //이미 user의 ShelterUuid를 알고 있는 상태에서 단순 비교
    boolean isSameShelterUserAndManager(String userShelterUuid, Manager manager);

    boolean isExistShelter(String shelterUuid);
}
