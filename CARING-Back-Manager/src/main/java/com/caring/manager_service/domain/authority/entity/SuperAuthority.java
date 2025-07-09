package com.caring.manager_service.domain.authority.entity;

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
@Table(name = "super_authority")
public class SuperAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "super_authority_id")
    private Long id;

    @Column(name = "default_auth", nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private SuperAuth superAuth;
}
