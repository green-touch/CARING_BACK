package com.caring.manager_service.presentation.manager.service;

import com.caring.manager_service.common.AuthorityDataInitializer;
import com.caring.manager_service.common.service.DatabaseCleanUp;
import com.caring.manager_service.domain.manager.entity.Manager;
import com.caring.manager_service.domain.manager.repository.ManagerRepository;
import com.caring.manager_service.presentation.manager.vo.request.RequestManager;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
class RegisterSuperManagerUseCaseTest {
    @Autowired
    RegisterSuperManagerUseCase registerSuperManagerUseCase;
    @Autowired
    ManagerRepository managerRepository;
    @Autowired
    ManagerAuthorityRepository managerAuthorityRepository;
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
    @DisplayName("SUPER 권한을 가진 매니저를 서버에 등록합니다.(등록 주체는 별도로 존재하지 않습니다.)")
    void registerSuperManagerUseCase() {
        // given
        RequestManager request = RequestManager.builder()
                .name("name")
                .password("password")
                .build();
        // when
        Long managerId = registerSuperManagerUseCase.execute(request);
        // then
        Optional<Manager> manager = managerRepository.findById(managerId);
        assertThat(manager).isPresent();
        List<ManagerAuthority> managerAuthorities = managerAuthorityRepository.findByManager(manager.get());
        assertThat(managerAuthorities.get(0).getAuthority().getManagerRole()).isEqualTo(ManagerRole.SUPER);

    }

}
