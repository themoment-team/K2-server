package com.moment.the.domain;

import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Table(name="Admin")
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminDomain {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminIdx;

    @Column
    private String adminName;

    @Column(unique = true)
    @Email
    private String adminId;

    @Column
    private String adminPwd;

    @Column
    @Enumerated(EnumType.STRING)
    private AuthEnum authEnum;

    public void acceptRole() {
        this.authEnum = AuthEnum.ROLE_Accepted;
    }
    public void waitRole(){
        this.authEnum = AuthEnum.ROLE_Waiting;
    }
}
