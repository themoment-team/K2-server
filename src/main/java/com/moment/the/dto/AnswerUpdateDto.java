package com.moment.the.dto;

import com.moment.the.domain.AnswerDomain;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AnswerUpdateDto {
    private String contents;

    public AnswerDomain toEntity(String contents){
        return AnswerDomain.builder()
                .answerContent(contents)
                .build();

    }
}
