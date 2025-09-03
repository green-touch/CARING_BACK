package com.caring.user_service.domain.command.business.domainService;

import com.caring.user_service.domain.command.entity.Commands;

public interface CommandsDomainService {

    Commands createCommand(String deviceId, String payload);
}
