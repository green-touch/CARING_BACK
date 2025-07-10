package com.caring.manager_service.presentation.manager.service;

import com.caring.manager_service.common.annotation.UseCase;
import com.caring.manager_service.domain.manager.business.adaptor.ManagerAdaptor;
import com.caring.manager_service.domain.manager.business.service.ManagerDomainService;
import com.caring.manager_service.domain.manager.entity.Manager;
import com.caring.manager_service.presentation.manager.vo.request.EditManagerInform;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@UseCase
@Transactional
@RequiredArgsConstructor
public class EditManagerInformUseCase {

    private final ManagerAdaptor managerAdaptor;
    private final ManagerDomainService managerDomainService;

    public Long execute(String managerCode, EditManagerInform editManagerInform) {

        Manager manager = managerAdaptor.queryByMemberCode(managerCode);
        managerDomainService.editProfile(editManagerInform);
        return manager.getId();
    }
}
