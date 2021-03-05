package com.moment.the.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "Auth")
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthDomain extends {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String authIdx;

    @Column
    private String authEmail;
}
