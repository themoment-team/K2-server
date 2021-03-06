package com.moment.the.domain;

import lombok.*;

import javax.persistence.*;

@Table(name = "Auth")
@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthDomain extends BaseTime{
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String authIdx;

    @Column
    private String authEmail;
}
