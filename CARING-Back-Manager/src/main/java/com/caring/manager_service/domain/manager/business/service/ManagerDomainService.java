
package com.caring.manager_service.domain.manager.business.service;

import com.caring.manager_service.domain.authority.entity.Authority;
import com.caring.manager_service.domain.manager.entity.Manager;
import com.caring.manager_service.domain.manager.entity.ManagerGroup;

public interface ManagerDomainService {

    Manager registerManager(String name, String password, Authority authority);

    ManagerGroup addUserToManagerGroup(Manager manager, String userUuid);
}
