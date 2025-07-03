package com.caring.manager_service.presentation.manager.service;

import com.caring.manager_service.common.annotation.UseCase;
import com.caring.manager_service.domain.manager.business.adaptor.ManagerAdaptor;
import com.caring.manager_service.domain.manager.entity.Manager;
import com.caring.manager_service.presentation.manager.vo.response.ResponseSpecificManager;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@UseCase
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetAllManagersUseCase {
    private final ManagerAdaptor managerAdaptor;

    public List<ResponseSpecificManager> execute() {
        List<Manager> managers = managerAdaptor.queryAll();
        return managers.stream()
                .map(ResponseSpecificManager::of)
                .toList();
    }
}
