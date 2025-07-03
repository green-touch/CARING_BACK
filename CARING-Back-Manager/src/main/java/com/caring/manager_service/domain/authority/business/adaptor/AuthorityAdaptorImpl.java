package com.caring.manager_service.domain.authority.business.adaptor;

import com.caring.manager_service.common.annotation.Adaptor;
import com.caring.manager_service.domain.authority.entity.Authority;
import com.caring.manager_service.domain.authority.entity.ManagerRole;
import com.caring.manager_service.domain.authority.repository.AuthorityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Adaptor
@RequiredArgsConstructor
public class AuthorityAdaptorImpl implements AuthorityAdaptor{

    private final AuthorityRepository authorityRepository;

    @Override
    public Authority queryByManagerRole(ManagerRole managerRole) {
        return authorityRepository.findByManagerRole(managerRole)
                .orElseThrow(() -> new RuntimeException("not found ManagerRole"));
    }

    @Override
    public List<Authority> queryAll() {
        return authorityRepository.findAll();
    }
}
