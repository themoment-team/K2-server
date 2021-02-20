package com.moment.the.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Table
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminDomain {
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
}
