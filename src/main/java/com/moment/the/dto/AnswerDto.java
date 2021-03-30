package com.moment.the.dto;

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
    private String content;

    public AnswerDomain toEntity(String content, AdminDomain adminDomain, TableDomain table){
        return AnswerDomain.builder()
                .answerContent(content)
                .tableDomain(table)
                .adminDomain(adminDomain)
                .build();
    }
}