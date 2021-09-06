package com.moment.the.uncomfortable;

import com.moment.the.answer.AnswerDomain;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity @Table(name = "uncomfortable")
@Getter @Builder
@NoArgsConstructor @AllArgsConstructor
public class UncomfortableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uncomfortableIdx;

    @Column
    private String content;

    @Column
    private int goods;

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    private AnswerDomain answerDomain;

    public void updateGoods(int goods){
        this.goods = goods;
    }

    public void updateAnswerDomain(AnswerDomain answerDomain){
        this.answerDomain = answerDomain;
    }
}
