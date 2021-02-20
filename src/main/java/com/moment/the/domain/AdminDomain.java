package com.moment.the.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Table
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminDomain implements UserDetails {
    @Id
    @Column
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminIdx;

    @Column
    private String adminName;

    @Column(unique = true)
    private String adminId;

    @Column
    private String adminPwd;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "salt_id")
    private SaltDomain salt;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
    @Override
    public String getPassword() {
        return getPassword();
    }
    @Override
    public String getUsername() {
        return getAdminId();
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
