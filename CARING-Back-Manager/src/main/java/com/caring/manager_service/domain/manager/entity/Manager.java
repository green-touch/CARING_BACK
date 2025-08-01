package com.caring.manager_service.domain.manager.entity;

import com.caring.manager_service.domain.auditing.entity.BaseTimeEntity;
import com.caring.manager_service.domain.authority.entity.PersonalSuperAuthority;
import com.caring.manager_service.presentation.manager.vo.request.EditManagerInform;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(of = "id", callSuper = false)
@Table(name = "manager")
public class Manager extends BaseTimeEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manager_id")
    private Long id;

    @Column(unique = true)
    private String managerUuid;
    @Column(name = "member_code", unique = true)
    private String memberCode;

    private String password;
    private String name;
    private String shelterUuid;
    private String phoneNumber;
    private String email;

    @Builder.Default
    @OneToMany(mappedBy = "manager", cascade = CascadeType.PERSIST)
    private List<PersonalSuperAuthority> personalSuperAuthorityList = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return personalSuperAuthorityList.stream()
                .map(personalSuperAuthority ->
                        new SimpleGrantedAuthority(
                                personalSuperAuthority.getSuperAuthority().getSuperAuth().getKey()
                        )
                )
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.memberCode;
    }

    public void groupedInShelter(String shelterUuid) {
        this.shelterUuid = shelterUuid;
    }

    public void updateProfile(EditManagerInform editManagerInform) {
        this.email = editManagerInform.getEmail();
        this.phoneNumber = editManagerInform.getPhoneNumber();
    }
}
