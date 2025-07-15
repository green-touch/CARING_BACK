package com.caring.manager_service.infra.user.vo.request;

import lombok.Builder;
import lombok.Data;
import lombok.Value;


@Value  // @Data 파생 어노테이션 : 필드 불변 추가
@Builder
public class RequestUserWithShelterUuid {
    //TODO 이름, 주민등록번호, 주소, 전화번, 비상연락망(list), 메모
    private String name;
    private String password;
    private String shelterUuid;
}
