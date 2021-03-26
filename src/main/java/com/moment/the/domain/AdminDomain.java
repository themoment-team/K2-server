package com.moment.the.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "admin")
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminDomain {
    @Id
    private Long adminIdx;

    @Column
    private String adminName;

    @Column
    private String adminId;

    @Column
    private String adminPwd;

    @Column
    @Enumerated(EnumType.STRING)
    private String adminAuth;

    // Dirty Check
    public void setAdminId(String adminId){
        this.adminId = adminId;
    }
}
