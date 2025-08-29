package com.caring.user_service.domain.command.repository;

import com.caring.user_service.domain.command.entity.Commands;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandsRepository extends JpaRepository<Commands, Long> {
}
