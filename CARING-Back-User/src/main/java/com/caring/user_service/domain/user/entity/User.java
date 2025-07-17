package com.caring.user_service.domain.user.entity;

import com.caring.user_service.domain.auditing.entity.BaseTimeEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(of = "id", callSuper = false)
@Table(name = "users")
public class User extends BaseTimeEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true)
    private String userUuid;

    @Column(name = "member_code", unique = true)
    private String memberCode;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String password;

    private String name;

    @Column(name = "shelter_uuid")
    private String shelterUuid;

    @Column(name = "birth_date")
    private String birthDate; // ex: 19980505 (String)

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "road_address") // 도로명 주소
    private String roadAddress;

    @Column(name = "detail_address") // 상세 주소
    private String detailAddress;

    @Column(name = "postal_code", length = 10) // 우편번호
    private String postalCode;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Column(name = "memo", length = 500)
    private String memo;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<EmergencyContact> emergencyContacts = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.role.getKey()));
    }

    @Override
    public String getUsername() {
        return this.getMemberCode();
    }

    public void groupedInShelter(String shelterUuid) {
        this.shelterUuid = shelterUuid;
    }

    public boolean isSamePassword(String encodedPassword) {
        return Objects.equals(this.password, encodedPassword);
    }

    public void changePassword(String encodedPassword) {
        this.password = encodedPassword;
    }

    public void changePhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void changeAddress(String roadAddress, String detailAddress) {
        if (!roadAddress.isEmpty())
            this.roadAddress = roadAddress;
        if (!detailAddress.isEmpty())
            this.detailAddress = detailAddress;
    }

    public void changeMemo(String memo) {
        this.memo = memo;
    }
}
