package com.moment.the.answer;



import com.moment.the.admin.AdminDomain;
import com.moment.the.answer.dto.AnswerDto;
import com.moment.the.uncomfortable.UncomfortableEntity;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity @Table(name = "answer")
@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor
public class AnswerDomain {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Long answerIdx;

    @Column(name = "content", length = 1000, nullable = false)
    private String content;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "uncomfortable_id", nullable = false)
    private UncomfortableEntity uncomfortableEntity;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="writer_admin_id", nullable = false)
    private AdminDomain adminDomain;

    // dirty checking.
    public void update(AnswerDto answerDto) {
        this.content = answerDto.getContent();
    }

    public void updateTableDomain(UncomfortableEntity uncomfortableEntity){
        this.uncomfortableEntity = uncomfortableEntity;
        this.uncomfortableEntity.updateAnswerDomain(this);
    }
}
