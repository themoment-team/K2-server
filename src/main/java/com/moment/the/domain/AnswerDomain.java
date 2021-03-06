package com.moment.the.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "Answer")
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerIdx;

    @Column
    @NotNull
    private String answerContent;

    @ManyToOne
    @JoinColumn(name = "boardIdx")
    private TableDomain table;

    public void update(String answerContent){
        this.answerContent = answerContent;
    }

}
