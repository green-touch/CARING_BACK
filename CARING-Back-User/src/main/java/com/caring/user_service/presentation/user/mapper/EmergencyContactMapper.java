package com.caring.user_service.presentation.user.mapper;

import com.caring.user_service.domain.user.entity.EmergencyContact;
import com.caring.user_service.presentation.dto.EmergencyContactDTO;
import com.caring.user_service.presentation.user.vo.RequestEmergencyContact;
import com.caring.user_service.presentation.user.vo.RequestEmergencyContactWithContactUuid;
import com.caring.user_service.presentation.user.vo.ResponseEmergencyContact;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = false))
public interface EmergencyContactMapper {

    ResponseEmergencyContact toResponse(EmergencyContact contact);

    List<ResponseEmergencyContact> toResponseList(List<EmergencyContact> contacts);

    EmergencyContactDTO toEmergencyContactDTO(RequestEmergencyContact requestEmergencyContact);

    @Mapping(target = "name", source = "contactName")
    @Mapping(target = "relationship", source = "contactRelationship")
    @Mapping(target = "phoneNumber", source = "contactPhoneNumber")
    EmergencyContactDTO toEmergencyContactDTO(RequestEmergencyContactWithContactUuid request);
}
