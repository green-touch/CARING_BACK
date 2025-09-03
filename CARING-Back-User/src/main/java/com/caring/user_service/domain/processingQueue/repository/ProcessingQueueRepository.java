package com.caring.user_service.domain.processingQueue.repository;

import com.caring.user_service.domain.processingQueue.entity.ProcessingQueue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Duration;

public interface ProcessingQueueRepository extends JpaRepository<ProcessingQueue, Long>, ProcessingQueueNativeRepository {

}