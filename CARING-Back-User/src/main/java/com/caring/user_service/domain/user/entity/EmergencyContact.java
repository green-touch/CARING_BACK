package com.caring.user_service.domain.user.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "user_emergency_contacts")
public class EmergencyContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // 외래 키
    private User user;

    @Column(unique = true)
    private String contactUuid;

    @Column(name = "contact_name", nullable = false)
    private String contactName;

    @Column(name = "contact_relationship")
    private String contactRelationship;

    @Column(name = "contact_phone_number", nullable = false)
    private String contactPhoneNumber;

    public void changeName(String contactName) {
        this.contactName = contactName;
    }

    public void changePhoneNumber(String contactPhoneNumber) {
        this.contactPhoneNumber = contactPhoneNumber;
    }

    public void changeRelationship(String contactRelationship) {
        this.contactRelationship = contactRelationship;
    }
}
