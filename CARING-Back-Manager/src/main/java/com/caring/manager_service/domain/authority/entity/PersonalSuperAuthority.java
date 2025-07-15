package com.caring.manager_service.domain.authority.entity;

import com.caring.manager_service.domain.auditing.entity.BaseTimeEntity;
import com.caring.manager_service.domain.manager.entity.Manager;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
@Table(
        name = "personal_super_authority",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"manager_id", "super_authority_id"})
        }
)
public class PersonalSuperAuthority extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "personal_super_authority_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Manager manager;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "super_authority_id")
    private SuperAuthority superAuthority;
}
