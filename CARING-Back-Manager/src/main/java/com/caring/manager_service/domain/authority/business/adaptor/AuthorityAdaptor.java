package com.caring.manager_service.domain.authority.business.adaptor;

import com.caring.manager_service.domain.authority.entity.Authority;
import com.caring.manager_service.domain.authority.entity.ManagerRole;

import java.util.List;

public interface AuthorityAdaptor {

    Authority queryByManagerRole(ManagerRole managerRole);

    List<Authority> queryAll();
}
