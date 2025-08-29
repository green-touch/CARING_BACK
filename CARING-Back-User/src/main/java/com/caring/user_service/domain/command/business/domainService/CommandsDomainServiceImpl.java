package com.caring.user_service.domain.command.business.domainService;

import com.caring.user_service.common.annotation.DomainService;
import com.caring.user_service.domain.command.entity.Commands;
import com.caring.user_service.domain.command.repository.CommandsRepository;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class CommandsDomainServiceImpl implements CommandsDomainService {

    private final CommandsRepository commandsRepository;

    @Override
    public Commands createCommand(String deviceId, String payload) {
        Commands commands = Commands.builder()
                .deviceId(deviceId)
                .payloadJson(payload)
                .build();
        return commandsRepository.save(commands);
    }
}
