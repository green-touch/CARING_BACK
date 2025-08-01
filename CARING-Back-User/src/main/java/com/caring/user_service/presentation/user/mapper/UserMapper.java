package com.caring.user_service.presentation.user.mapper;

import com.caring.user_service.domain.user.entity.User;
import com.caring.user_service.presentation.dto.UserDTO;
import com.caring.user_service.presentation.user.vo.RequestUser;
import com.caring.user_service.presentation.user.vo.ResponseUser;
import com.caring.user_service.presentation.user.vo.ResponseUserHomeInfo;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

/**
 * vo는 setter를 쓰지 않기 때문에 impl생성이 자동으로 builder가 될 수 있도록 아래와 같이 설정해줘야한다.
 * 이때 build.gradle의 순서가 중요하니 주의
 */
@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = false), uses={ EmergencyContactMapper.class })
public interface UserMapper {

//    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    ResponseUser toResponseUserVo(User user);

    ResponseUserHomeInfo toResponseUserHomeInfoVo(User user);

    UserDTO toDTO(RequestUser requestUser);
}
