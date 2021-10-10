package com.moment.the.uncomfortable;

import com.moment.the.answer.AnswerDomain;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity @Table(name = "uncomfortable")
@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor
public class UncomfortableDomain {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uncomfortable_id")
    private Long uncomfortableIdx;

    @Column(name = "content")
    private String content;

    @Column(name = "goods")
    private int goods;

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "answer_id")
    private AnswerDomain answerDomain;

    public void updateGoods(int goods){
        this.goods = goods;
    }

    public void updateAnswerDomain(AnswerDomain answerDomain){
        this.answerDomain = answerDomain;
    }
}
