package com.moment.the.domain;

import com.moment.the.dto.AnswerDto;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

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

    @OneToOne(mappedBy = "answerDomain", fetch = LAZY)
    @JoinColumn(name = "boardIdx")
    private TableDomain tableDomain;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="writer")
    private AdminDomain adminDomain;

    // dirty checking.
    public void update(AnswerDto answerDto) {
        this.answerContent = answerDto.getContent();
    }

    public void updateTableDomain(TableDomain tableDomain){
        this.tableDomain = tableDomain;
        this.tableDomain.updateAnswerDomain(this);
    }
}
