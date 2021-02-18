package com.moment.the.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<AnswerDomain> answer;
}
