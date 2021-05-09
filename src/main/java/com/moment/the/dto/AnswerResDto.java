package com.moment.the.dto;

import lombok.*;

@Builder
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class AnswerResDto {

    private Long answerIdx;
    private String answerContents;
    private String adminName;
}
