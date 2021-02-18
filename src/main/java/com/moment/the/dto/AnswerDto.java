package com.moment.the.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.moment.the.domain.AnswerDomain;
import com.moment.the.domain.TableDomain;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDto {

    private Long tableIdx;
    private String content;

    @JsonIgnore
    private TableDomain table;

    public AnswerDomain toEntity(){
        return AnswerDomain.builder()
                .table(this.table)
                .answerContent(this.content)
                .build();
    }
}