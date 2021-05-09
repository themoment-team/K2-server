package com.moment.the.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.moment.the.domain.AdminDomain;
import com.moment.the.domain.AnswerDomain;
import com.moment.the.domain.TableDomain;
import lombok.*;

@Builder
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class AnswerDto {
    private String content;

    @JsonIgnore
    private AdminDomain adminDomain;

    public AnswerDomain toEntity(){
        return AnswerDomain.builder()
                .answerContent(this.content)
                .adminDomain(this.adminDomain)
                .build();
    }
}