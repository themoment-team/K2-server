package com.moment.the.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.moment.the.domain.AdminDomain;
import com.moment.the.domain.AnswerDomain;
import com.moment.the.domain.TableDomain;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDto {
    // FK
    @JsonIgnore
    private Long boardIdx;
    @JsonIgnore
    private TableDomain tableDomain;
    @JsonIgnore
    private AdminDomain adminDomain;

    // Answer Domain
    private String content;

    public AnswerDomain toEntity(){
        return AnswerDomain.builder()
                .tableDomain(this.tableDomain)
                .answerContent(this.content)
                .build();
    }
}