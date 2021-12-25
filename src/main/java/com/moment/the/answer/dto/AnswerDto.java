package com.moment.the.answer.dto;

import com.moment.the.admin.AdminDomain;
import com.moment.the.answer.AnswerDomain;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE) @AllArgsConstructor
public class AnswerDto {
    private String content;

    public AnswerDomain toEntity(AdminDomain adminDomain){
        return AnswerDomain.builder()
                .content(this.content)
                .adminDomain(adminDomain)
                .build();
    }
}