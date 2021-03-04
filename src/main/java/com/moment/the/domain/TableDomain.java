package com.moment.the.domain;

import lombok.*;

import javax.persistence.*;

@Table(name = "Board")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TableDomain {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardIdx;
    @Column
    private String content;
    @Column
    private int goods;
}
