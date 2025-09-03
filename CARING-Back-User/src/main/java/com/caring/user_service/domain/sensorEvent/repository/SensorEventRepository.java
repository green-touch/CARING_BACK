package com.caring.user_service.domain.sensorEvent.repository;

import com.caring.user_service.domain.sensorEvent.entity.SensorEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorEventRepository extends JpaRepository<SensorEvent, Long> {
}
