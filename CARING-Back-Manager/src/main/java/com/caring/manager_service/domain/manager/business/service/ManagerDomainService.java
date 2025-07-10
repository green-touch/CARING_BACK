
package com.caring.manager_service.domain.manager.business.service;

import com.caring.manager_service.domain.manager.entity.Manager;
import com.caring.manager_service.domain.manager.entity.ManagerGroup;
import com.caring.manager_service.presentation.manager.vo.request.EditManagerInform;

public interface ManagerDomainService {

    Manager registerManager(String name, String password);

    ManagerGroup addUserToManagerGroup(Manager manager, String userUuid);

    Manager editProfile(Manager manager, EditManagerInform editManagerInform);
}
