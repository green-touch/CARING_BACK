package com.caring.manager_service.domain.manager.business.validator;

import com.caring.manager_service.domain.manager.entity.Manager;

public interface ManagerValidator {

    boolean isSuper(Manager manager);

    void checkPasswordEncode(Manager manager, String password);

    void checkName(String name);

    void checkPassword(String password);
}
