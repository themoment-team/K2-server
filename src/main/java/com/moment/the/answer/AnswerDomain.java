package com.moment.the.answer;



import com.moment.the.admin.AdminDomain;
import com.moment.the.answer.dto.AnswerDto;
import com.moment.the.uncomfortable.TableDomain;
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

    @Column(length = 1000, nullable = false)
    @NotNull
    private String answerContent;

    @OneToOne(mappedBy = "answerDomain", fetch = LAZY)
    @JoinColumn(name = "boardIdx", nullable = false)
    private TableDomain tableDomain;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="writer", nullable = false)
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
