package com.moment.the.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long answerIdx;

    @Column
    @NotNull
    String answerContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardIdx")
    private TableDomain table;
}
