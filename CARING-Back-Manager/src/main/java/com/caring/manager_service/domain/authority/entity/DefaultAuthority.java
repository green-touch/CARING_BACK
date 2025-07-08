package com.caring.manager_service.domain.authority.entity;

import com.caring.manager_service.domain.auditing.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
@Table(name = "default_authority")
public class DefaultAuthority extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "default_authority_id")
    private Long id;

    @Column(name = "default_auth", nullable = false)
    @Enumerated(EnumType.STRING)
    private DefaultAuth defaultAuth;

}
