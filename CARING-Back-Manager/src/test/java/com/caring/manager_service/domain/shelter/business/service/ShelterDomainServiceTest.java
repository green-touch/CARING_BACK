package com.caring.manager_service.domain.shelter.business.service;

import com.caring.manager_service.common.AuthorityDataInitializer;
import com.caring.manager_service.common.service.DatabaseCleanUp;
import com.caring.manager_service.domain.authority.business.adaptor.AuthorityAdaptor;
import com.caring.manager_service.domain.authority.entity.Authority;
import com.caring.manager_service.domain.authority.entity.ManagerRole;
import com.caring.manager_service.domain.manager.business.service.ManagerDomainService;
import com.caring.manager_service.domain.manager.entity.Manager;
import com.caring.manager_service.domain.shelter.entity.Shelter;
import com.caring.manager_service.domain.shelter.repository.ShelterRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
class ShelterDomainServiceTest {

    @Autowired
    ShelterDomainService shelterDomainService;
    @Autowired
    ShelterRepository shelterRepository;
    @Autowired
    ManagerDomainService managerDomainService;
    @Autowired
    AuthorityDataInitializer authorityDataInitializer;
    @Autowired
    AuthorityAdaptor authorityAdaptor;
    @Autowired
    DatabaseCleanUp databaseCleanUp;

    @BeforeEach
    void setup() {
        authorityDataInitializer.initAuthorityData();
    }

    @AfterEach
    void cleanUp() {
        databaseCleanUp.truncateAllEntity();
    }
    @Test
    @DisplayName("보호소의 이름과 위치를 작성하여 shelter 데이터를 입력합니다.")
    void registerShelter(){
        //given
        String name = "shelter";
        String location = "location";
        //when
        Long shelterId = shelterDomainService.registerShelter(name, location).getId();
        //then
        Optional<Shelter> findShelter = shelterRepository.findById(shelterId);
        assertThat(findShelter).isPresent();
        assertThat(findShelter.get().getName()).isEqualTo(name);
    }

    @Test
    @Transactional
    @DisplayName("데이터베이스에 저장된 매니저와 보호소의 랜덤 난수를 통해 연관관계를 맺어줍니다.(매니저는 보호소 소속이 됩니다)")
    void addShelterStaff(){
        //given
        Authority authority = authorityAdaptor.queryByManagerRole(ManagerRole.MANAGE);
        Shelter shelter = shelterDomainService.registerShelter("shelter", "location");
        Manager manager = managerDomainService.registerManager("manager", "password", authority);
        //when
        shelterDomainService.addShelterStaff(shelter.getShelterUuid(), manager);
        //then
        assertThat(manager.getShelterUuid()).isEqualTo(shelter.getShelterUuid());
    }

    /*해당 부분은 앞으로 user Service에서 책임질 예정이므로 제외된 테스트입니다..!*/
//    @Test
//    @Transactional
//    @DisplayName("데이터베이스에 저장된 유저와 보호소의 랜덤 난수를 통해 연관관계를 맺어줍니다.(유저는 보호소 소속이 됩니다)")
//    void addShelterGroup(){
//        //given
//        Authority authority = authorityAdaptor.queryByManagerRole(ManagerRole.MANAGE);
//        Shelter shelter = shelterDomainService.registerShelter("shelter", "location");
//        User user = userDomainService.registerUser("password", "name");
//        //when
//        shelterDomainService.addShelterGroup(shelter.getShelterUuid(), user);
//        //then
//        assertThat(user.getShelterUuid()).isEqualTo(shelter.getShelterUuid());
//    }

}
