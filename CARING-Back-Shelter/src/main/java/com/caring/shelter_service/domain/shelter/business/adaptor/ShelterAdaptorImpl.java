package com.caring.shelter_service.domain.shelter.business.adaptor;

import com.caring.shelter_service.common.annotation.Adaptor;
import com.caring.shelter_service.domain.shelter.entity.Shelter;
import com.caring.shelter_service.domain.shelter.repository.ShelterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Adaptor
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ShelterAdaptorImpl implements ShelterAdaptor{

    private final ShelterRepository shelterRepository;

    @Override
    public Page<Shelter> getShelterList(Pageable pageable) {

        return shelterRepository.findAll(pageable);
    }
}
