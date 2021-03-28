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
    // Have to Type
    private String content;
    private Long boardIdx;
    // JsonIgnore
    @JsonIgnore
    private TableDomain tableDomain;
    @JsonIgnore
    private AdminDomain adminDomain;

    public AnswerDomain toEntity(AdminDomain adminDomain){
        return AnswerDomain.builder()
                .tableDomain(this.tableDomain)
                .answerContent(this.content)
                .adminDomain(adminDomain)
                .build();
    }
}