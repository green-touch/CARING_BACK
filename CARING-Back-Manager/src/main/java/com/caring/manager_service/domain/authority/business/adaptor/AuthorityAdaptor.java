package com.caring.manager_service.domain.authority.business.adaptor;

import com.caring.manager_service.domain.authority.entity.PersonalSuperAuthority;
import com.caring.manager_service.domain.authority.entity.SuperAuth;
import com.caring.manager_service.domain.authority.entity.SuperAuthority;
import com.caring.manager_service.domain.manager.entity.Manager;

import java.util.List;
import java.util.Set;

public interface AuthorityAdaptor {

    Set<PersonalSuperAuthority> queryCurrentPersonalSuperAuthority(Manager manager);

    SuperAuthority queryBySuperAuth(SuperAuth superAuth);

    List<SuperAuthority> queryAllSuperAuthority();
}
