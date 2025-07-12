package com.caring.user_service.presentation.user.mapper;

import com.caring.user_service.domain.user.entity.EmergencyContact;
import com.caring.user_service.presentation.user.vo.ResponseEmergencyContact;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = false))
public interface EmergencyContactMapper {

    ResponseEmergencyContact toResponse(EmergencyContact contact);

    List<ResponseEmergencyContact> toResponseList(List<EmergencyContact> contacts);
}
