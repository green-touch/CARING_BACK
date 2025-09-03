package com.caring.manager_service.domain.manager.business.adaptor;

import com.caring.manager_service.common.annotation.Adaptor;
import com.caring.manager_service.domain.manager.entity.ManagerGroup;
import com.caring.manager_service.domain.manager.repository.ManagerGroupRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Adaptor
@RequiredArgsConstructor
public class ManagerGroupAdaptorImpl implements ManagerGroupAdaptor{

    private final ManagerGroupRepository managerGroupRepository;

    @Override
    public List<String> queryUserUuidListByManagerCode(String managerCode) {
        return managerGroupRepository.findUserUuidListByManagerMemberCode(managerCode);
    }
}
