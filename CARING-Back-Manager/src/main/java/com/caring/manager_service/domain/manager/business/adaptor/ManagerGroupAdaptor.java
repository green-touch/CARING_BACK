package com.caring.manager_service.domain.manager.business.adaptor;

import com.caring.manager_service.domain.manager.entity.ManagerGroup;

import java.util.List;

public interface ManagerGroupAdaptor {

    List<String> queryUserUuidListByManagerCode(String managerCode);
}
