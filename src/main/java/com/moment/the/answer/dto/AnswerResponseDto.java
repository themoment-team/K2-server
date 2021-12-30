package com.moment.the.answer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class AnswerResDto {

    private Long answerIdx;
    private String title;
    @JsonProperty("content")
    private String content;
    private String writer;

}
