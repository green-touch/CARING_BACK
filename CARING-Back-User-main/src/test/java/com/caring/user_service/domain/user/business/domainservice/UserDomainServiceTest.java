package com.caring.user_service.domain.user.business.domainservice;

import com.caring.user_service.common.service.DatabaseCleanUp;
import com.caring.user_service.domain.user.entity.Role;
import com.caring.user_service.domain.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
class UserDomainServiceTest {

    @Autowired
    UserDomainService userDomainService;
    @Autowired
    DatabaseCleanUp databaseCleanUp;

    @AfterEach
    void cleanUp() {
        databaseCleanUp.truncateAllEntity();
    }

    @Test
    @DisplayName("이름과 패스워드를 입력하여 유저를 등록합니다.")
    void registerUser() {
        // given
        String name = "name";
        String password = "password";

        // when
        User user = userDomainService.registerUser(name, password);

        // then
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getRole()).isEqualTo(Role.USER);
        assertThat(user.getUserUuid()).isNotNull();
        assertThat(user.getMemberCode()).isNotNull();
        assertThat(user.getPassword()).isNotEqualTo(password); // 암호화 확인
    }

    @Test
    @DisplayName("이름이 null인 경우 예외가 발생합니다.")
    void registerUserWithNullName() {
        // given
        String name = null;
        String password = "password";

        // when & then
        assertThatThrownBy(() -> userDomainService.registerUser(name, password))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이름은 null일 수 없습니다");
    }

    @Test
    @DisplayName("비밀번호가 null인 경우 예외가 발생합니다.")
    void registerUserWithNullPassword() {
        // given
        String name = "name";
        String password = null;

        // when & then
        assertThatThrownBy(() -> userDomainService.registerUser(name, password))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("비밀번호는 null일 수 없습니다");
    }

    @Test
    @DisplayName("이름이 빈 문자열인 경우 예외가 발생합니다.")
    void registerUserWithEmptyName() {
        // given
        String name = "";
        String password = "password";

        // when & then
        assertThatThrownBy(() -> userDomainService.registerUser(name, password))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이름은 빈 문자열일 수 없습니다");
    }

    @Test
    @DisplayName("비밀번호가 빈 문자열인 경우 예외가 발생합니다.")
    void registerUserWithEmptyPassword() {
        // given
        String name = "name";
        String password = "";

        // when & then
        assertThatThrownBy(() -> userDomainService.registerUser(name, password))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("비밀번호는 빈 문자열일 수 없습니다");
    }

    @Test
    @DisplayName("생성된 memberCode가 올바른 형식인지 확인합니다.")
    void verifyMemberCodeFormat() {
        // given
        String name = "name";
        String password = "password";

        // when
        User user = userDomainService.registerUser(name, password);

        // then
        assertThat(user.getMemberCode())
                .startsWith("CRU#")
                .hasSize(11); // CRU# + 7자리 랜덤 문자열
    }

    @Test
    @DisplayName("생성된 userUuid가 올바른 형식인지 확인합니다.")
    void verifyUserUuidFormat() {
        // given
        String name = "name";
        String password = "password";

        // when
        User user = userDomainService.registerUser(name, password);

        // then
        // uuid 형식 확인 (8-4-4-4-12)
        assertThat(user.getUserUuid()).matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$");
    }

    @Test
    @DisplayName("이름, 비밀번호, 보호소 UUID를 입력하여 유저를 등록합니다.")
    void registerUserWithShelterUuid() {
        // given
        String name = "user";
        String password = "password";
        String shelterUuid = "shelter-1234";

        // when
        User user = userDomainService.registerUserWithShelterUuid(name, password, shelterUuid);

        // then
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getRole()).isEqualTo(Role.USER);
        assertThat(user.getUserUuid()).isNotNull();
        assertThat(user.getPassword()).isNotEqualTo(password); // 암호화 확인
        assertThat(user.getShelterUuid()).isEqualTo(shelterUuid);
        assertThat(user.getMemberCode())
                .startsWith("CRU#")
                .hasSize(11); // CRU# + 7자리 랜덤 문자열
    }
}
