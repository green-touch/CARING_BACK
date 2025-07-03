package com.caring.user_service.presentation.user.mapper;

import com.caring.user_service.domain.user.entity.User;
import com.caring.user_service.presentation.user.vo.ResponseUser;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * vo는 setter를 쓰지 않기 때문에 impl생성이 자동으로 builder가 될 수 있도록 아래와 같이 설정해줘야한다.
 * 이때 build.gradle의 순서가 중요하니 주의
 */
@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = false))
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    ResponseUser toResponseUserVo(User user);

}
