package com.moment.the.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminDomain implements UserDetails {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminIdx;

    @Column
    private String adminName;

    @Column
    private String adminId;

    @Column
    private String adminPwd;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    // Dirty Check
    public void setAdminId(String adminId){
        this.adminId = adminId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.adminPwd;
    }

    @Override
    public String getUsername() {
        return this.adminId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
