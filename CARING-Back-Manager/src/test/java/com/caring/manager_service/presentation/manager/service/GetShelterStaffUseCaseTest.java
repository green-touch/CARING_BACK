package com.caring.manager_service.presentation.manager.service;

import com.caring.manager_service.common.AuthorityDataInitializer;
import com.caring.manager_service.common.service.DatabaseCleanUp;
import com.caring.manager_service.domain.manager.business.service.ManagerDomainService;
import com.caring.manager_service.domain.manager.entity.Manager;
import com.caring.manager_service.domain.shelter.business.service.ShelterDomainService;
import com.caring.manager_service.domain.shelter.entity.Shelter;
import com.caring.manager_service.presentation.manager.vo.response.ResponseSpecificManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
class GetShelterStaffUseCaseTest {
    @Autowired
    GetShelterStaffUseCase getShelterStaffUseCase;
    @Autowired
    ShelterDomainService shelterDomainService;
    @Autowired
    ManagerDomainService managerDomainService;
    @Autowired
    AuthorityAdaptor authorityAdaptor;
    @Autowired
    AuthorityDataInitializer authorityDataInitializer;
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
    @Transactional
    @DisplayName("특정 보호소 소속 직원들을 조회합니다.")
    void getShelterStaffUseCase() {
        // given
        Authority managerAuth = authorityAdaptor.queryByManagerRole(ManagerRole.MANAGE);
        Authority superAuth = authorityAdaptor.queryByManagerRole(ManagerRole.SUPER);
        Manager manager1 = managerDomainService.registerManager("name1", "password", managerAuth);
        Manager manager2 = managerDomainService.registerManager("name2", "password", superAuth);
        Manager manager3 = managerDomainService.registerManager("name3", "password", superAuth);

        Shelter shelter1 = shelterDomainService.registerShelter("shelter1", "location");
        Shelter shelter2 = shelterDomainService.registerShelter("shelter2", "location");
        shelterDomainService.addShelterStaff(shelter1.getShelterUuid(), manager1);
        shelterDomainService.addShelterStaff(shelter1.getShelterUuid(), manager2);
        shelterDomainService.addShelterStaff(shelter2.getShelterUuid(), manager3);
        // when
        List<ResponseSpecificManager> result1 = getShelterStaffUseCase.execute(shelter1.getShelterUuid());
        List<ResponseSpecificManager> result2 = getShelterStaffUseCase.execute(shelter2.getShelterUuid());
        // then
        assertThat(result1.size()).isEqualTo(2);
        assertThat(result2.size()).isOne();
    }

}
