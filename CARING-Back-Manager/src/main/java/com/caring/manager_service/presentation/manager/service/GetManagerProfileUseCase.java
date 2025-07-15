package com.caring.manager_service.presentation.manager.service;

import com.caring.manager_service.common.annotation.UseCase;
import com.caring.manager_service.domain.manager.business.adaptor.ManagerAdaptor;
import com.caring.manager_service.presentation.manager.vo.response.ResponseSpecificManager;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetManagerProfileUseCase {

    private final ManagerAdaptor managerAdaptor;

    public ResponseSpecificManager execute(String managerCode) {
        return ResponseSpecificManager.of(managerAdaptor.queryByMemberCode(managerCode));
    }
}
