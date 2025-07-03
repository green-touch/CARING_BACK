package com.caring.manager_service.common.external;

import com.caring.manager_service.common.external.user.UserFeignClient;
import com.caring.manager_service.common.external.user.dto.RequestUserRegister;
import com.caring.manager_service.common.external.user.dto.ResponseUserShelterUuid;
import com.caring.manager_service.common.external.user.dto.ResponseUserUuid;
import com.caring.manager_service.presentation.manager.vo.response.ResponseUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.http.MediaType;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {UserFeignClientTest.WireMockConfig.class})
@EnableConfigurationProperties
@ActiveProfiles("test")
@SpringBootTest
class UserFeignClientTest {

    @Autowired
    private WireMockServer wireMockServer;

    @Autowired
    private UserFeignClient userFeignClient;

    @BeforeEach
    public void setup() throws Exception {
        UserMock.setup(wireMockServer);
    }

    @Test
    public void getUserShelterUuidSuccess() {
        String testUserUuid = "test-user-uuid";

        ResponseUserShelterUuid response = userFeignClient.getUserShelterUuid(testUserUuid);

        assertThat(response).isNotNull();
        assertThat(response.getShelterUuid()).isEqualTo("mock-shelter-uuid");
    }

    @Test
    public void registerUserSuccess() {
        RequestUserRegister request = new RequestUserRegister(
                                "mockuser", "mockpass", "mockShelterUuid");
        ResponseUserUuid response = userFeignClient.registerUser(request);

        assertThat(response).isNotNull();
        assertThat(response.getUserId()).isEqualTo(1L);
    }

    @Test
    public void queryUsersByUuidListSuccess() {
        List<ResponseUser> response = userFeignClient.queryUsersByUuidList(List.of("uuid1", "uuid2"));

        assertThat(response).isNotNull();
        assertThat(response).hasSize(2);
        assertThat(response.get(0).getUserUuid()).isEqualTo("uuid1");
    }

    @TestConfiguration
    static class WireMockConfig {
        @Bean(initMethod = "start", destroyMethod = "stop")
        public WireMockServer mockUserService() {
            return new WireMockServer(9651); // WireMock 포트
        }
    }

    static class UserMock {
        public static void setup(WireMockServer server) throws Exception {
            ObjectMapper mapper = new ObjectMapper();

            //user의 shelterUuid 조회 응답
            server.stubFor(get(urlPathMatching("/internal/users/.*/shelterUuid"))
                    .willReturn(aResponse()
                            .withStatus(200)
                            .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                            .withBody(mapper.writeValueAsString(new ResponseUserShelterUuid("mock-shelter-uuid")))
                    )
            );

            // 회원가입 응답
            server.stubFor(post(urlEqualTo("/internal/users/register"))
                    .willReturn(aResponse()
                            .withStatus(200)
                            .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                            .withBody(mapper.writeValueAsString(new ResponseUserUuid(1L, "uuid")))
                    )
            );

            // uuid 리스트로 사용자 조회
            server.stubFor(post(urlEqualTo("/internal/users/byUuidList"))
                    .willReturn(aResponse()
                            .withStatus(200)
                            .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                            .withBody(mapper.writeValueAsString(List.of(
                                    new ResponseUser("유저1", "uuid1", "M01"),
                                    new ResponseUser("유저2", "uuid2", "M02")
                            )))
                    )
            );
        }
    }

}
