package com.moment.the.domain;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Table(name = "Board")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TableDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardIdx;
    @Column
    private String content;
    @Column
    private int goods;

    @OneToOne(fetch = LAZY)
    private AnswerDomain answerDomain;

    public void updateGoods(int goods){
        this.goods = goods;
    }

    public void updateAnswerDomain(AnswerDomain answerDomain){
        this.answerDomain = answerDomain;
    }
}
