package com.caring.manager_service.presentation.manager.service;

import com.caring.manager_service.common.annotation.UseCase;
import com.caring.manager_service.domain.manager.business.adaptor.ManagerAdaptor;
import com.caring.manager_service.domain.manager.entity.Manager;
import com.caring.manager_service.presentation.manager.vo.response.ResponseManagerAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@UseCase
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetManagerAccountForTestUseCase {

    private final ManagerAdaptor managerAdaptor;

    public List<ResponseManagerAccount> execute() {
        List<Manager> managerList = managerAdaptor.queryAll();
        return managerList.stream()
                .map(manager -> ResponseManagerAccount.builder()
                        .memberCode(manager.getMemberCode())
                        .name(manager.getName())
                        .managerUuid(manager.getManagerUuid())
                        .build())
                .collect(Collectors.toList());
    }
}
