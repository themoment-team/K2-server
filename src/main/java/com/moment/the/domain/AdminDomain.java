package com.moment.the.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table
@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdminDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminIdx;

    @Column
    @NotNull
    private String adminName;

    @Column
    @NotNull
    private String adminId;

    @Column
    @NotNull
    private String adminPwd;
}
