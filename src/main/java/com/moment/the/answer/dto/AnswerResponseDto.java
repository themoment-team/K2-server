package com.moment.the.answer.dto;

import lombok.*;

@Builder
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class AnswerResponseDto {

    private Long answerIdx;
    private String title;
    private String content;
    private String writer;

}
