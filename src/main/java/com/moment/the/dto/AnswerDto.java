package com.moment.the.dto;

import com.moment.the.domain.AnswerDomain;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDto {

    private Long tableIdx;
    private String content;

    public AnswerDomain toEntity(){
        return AnswerDomain.builder()
                .tableIdx(this.tableIdx)
                .answerContent(this.content)
                .build();
    }
}