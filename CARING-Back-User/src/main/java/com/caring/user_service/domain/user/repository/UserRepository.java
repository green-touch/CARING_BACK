package com.caring.user_service.domain.user.repository;

import com.caring.user_service.domain.user.entity.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByMemberCode(@Param("memberCode") String memberCode);

    Optional<User> findByUserUuid(String userUuid);

    List<User> findByUserUuidIn(List<String> userUuids);
}
