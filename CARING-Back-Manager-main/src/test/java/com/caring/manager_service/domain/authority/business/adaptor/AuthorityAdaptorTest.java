package com.caring.manager_service.domain.authority.business.adaptor;

import com.caring.manager_service.common.AuthorityDataInitializer;
import com.caring.manager_service.domain.authority.entity.Authority;
import com.caring.manager_service.domain.authority.entity.ManagerRole;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
class AuthorityAdaptorTest {

    @Autowired
    AuthorityAdaptor authorityAdaptor;
    @Autowired
    AuthorityDataInitializer authorityDataInitializer;

    @BeforeEach
    void setup() {
        authorityDataInitializer.initAuthorityData();
    }

    @Test
    @DisplayName("ManagerRole enum의 키값을 통해 authority entity를 불러옵니다.")
    void queryByManagerRole(){
        //given

        //when
        Authority managerAuthority = authorityAdaptor.queryByManagerRole(ManagerRole.MANAGE);
        Authority superAuthority = authorityAdaptor.queryByManagerRole(ManagerRole.SUPER);
        Authority adminAuthority = authorityAdaptor.queryByManagerRole(ManagerRole.ADMIN);
        //then
        assertThat(managerAuthority).isNotNull();
        assertThat(superAuthority).isNotNull();
        assertThat(adminAuthority).isNotNull();
        assertThat(managerAuthority.getManagerRole()).isEqualTo(ManagerRole.MANAGE);
        assertThat(superAuthority.getManagerRole()).isEqualTo(ManagerRole.SUPER);
        assertThat(adminAuthority.getManagerRole()).isEqualTo(ManagerRole.ADMIN);

    }

    @Test
    @DisplayName("queryAll을 통해 모든 권한을 조회할 수 있습니다")
    void loadAllAuthorities() {
        //given
        // AuthorityDataInitializer가 이미 3개의 권한을 저장한 상태

        //when
        List<Authority> allAuthorities = authorityAdaptor.queryAll();

        //then
        assertThat(allAuthorities).isNotNull().hasSize(3);
        assertThat(allAuthorities)
                .extracting(Authority::getManagerRole)
                .containsExactlyInAnyOrder(ManagerRole.MANAGE, ManagerRole.SUPER, ManagerRole.ADMIN);
    }

}
