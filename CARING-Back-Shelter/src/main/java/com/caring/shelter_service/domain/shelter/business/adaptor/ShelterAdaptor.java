package com.caring.shelter_service.domain.shelter.business.adaptor;

import com.caring.shelter_service.domain.shelter.entity.Shelter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ShelterAdaptor {

    Page<Shelter> getShelterList(Pageable pageable);
}
