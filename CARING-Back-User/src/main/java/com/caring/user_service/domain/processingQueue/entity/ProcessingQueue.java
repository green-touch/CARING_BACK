package com.caring.user_service.domain.processingQueue.entity;

import com.caring.user_service.domain.auditing.entity.BaseTimeEntity;
import com.caring.user_service.domain.sensorEvent.entity.SensorEvent;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.time.LocalDateTime;

/**
 * pipeline flow
 * 1. insert sensor & enqueue
 * 2. pickBatch(큐로부터 status가 PENDING/FAILED 추출)
 * 3. running logic of calculating sensor
 * 4. convert status to DONE or FAILED
 */
@Table(name="processing_queue",
        indexes={
                @Index(name="ix_pq_pick", columnList="status, next_run_at, pq_id"),
                @Index(name="ix_pq_lease", columnList="status, lease_until")
        })
@Entity
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProcessingQueue extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "pq_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensor_event_id")
    private SensorEvent sensorEvent;

    private String deviceId;            //device 식별자
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProcessStatus status;
    private Integer attempt;            //시도횟수
    private LocalDateTime nextRunAt;    //다음 실행 시각
    private LocalDateTime leaseUntil;   //가시성 타임아웃
    private String claimedBy;           //이 작업을 할당받은 워커 ID
    private String lastError;           //실패 메세지
}
