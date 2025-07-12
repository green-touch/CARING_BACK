package com.caring.user_service.presentation.user.service;

import com.caring.user_service.common.annotation.UseCase;
import com.caring.user_service.domain.user.business.adaptor.UserAdaptor;
import com.caring.user_service.domain.user.business.domainservice.EmergencyContactDomainService;
import com.caring.user_service.domain.user.entity.User;
import com.caring.user_service.presentation.user.vo.RequestEmergencyContact;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class AddEmergencyContactsUseCase {

    private final UserAdaptor userAdaptor;
    private final EmergencyContactDomainService emergencyContactDomainService;

    public void execute(RequestEmergencyContact request) {
        User user = userAdaptor.queryUserByMemberCode(request.getMemberCode());

        emergencyContactDomainService.addEmergencyContact(user, request.getContactName(),
                request.getContactRelationship(), request.getContactPhoneNumber());
    }
}
