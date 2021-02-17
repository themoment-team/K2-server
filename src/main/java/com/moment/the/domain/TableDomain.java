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

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumns(value = "answerIdx")
    private List<AnswerDomain> answer;
}
