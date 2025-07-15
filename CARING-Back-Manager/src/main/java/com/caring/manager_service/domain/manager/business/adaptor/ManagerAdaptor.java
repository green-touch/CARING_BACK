package com.caring.manager_service.domain.manager.business.adaptor;

import com.caring.manager_service.domain.manager.entity.Manager;

import java.util.List;

public interface ManagerAdaptor {

    Manager queryByMemberCode(String memberCode);

    Manager queryByManagerUuid(String managerUuid);

    List<Manager> queryByShelter(String shelterUuid);

    List<Manager> queryAll();

    List<String> queryUserUuidsByManager(Manager manager);

}
