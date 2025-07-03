package com.caring.manager_service.domain.shelter.business.adaptor;

import com.caring.manager_service.common.service.DatabaseCleanUp;
import com.caring.manager_service.domain.shelter.business.service.ShelterDomainService;
import com.caring.manager_service.domain.shelter.entity.Shelter;
import com.caring.manager_service.domain.shelter.repository.ShelterRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
class ShelterAdaptorTest {

    @Autowired
    ShelterAdaptor shelterAdaptor;
    @Autowired
    ShelterDomainService shelterDomainService;
    @Autowired
    ShelterRepository shelterRepository;
    @Autowired
    DatabaseCleanUp databaseCleanUp;

    @AfterEach
    void cleanUp() {
        databaseCleanUp.truncateAllEntity();
    }

    @Test
    @DisplayName("shelter uuid를 통해 shelter entity를 불러옵니다.")
    void queryByShelterUuid(){
        //given
        String name = "shelter";
        String location = "location";

        Long shelterId = shelterDomainService.registerShelter(name, location).getId();
        Shelter shelter = shelterRepository.findById(shelterId).orElseThrow();
        //when
        Shelter findShelter = shelterAdaptor.queryByShelterUuid(shelter.getShelterUuid());
        //then
        assertThat(findShelter.getName()).isEqualTo(name);
    }

}
