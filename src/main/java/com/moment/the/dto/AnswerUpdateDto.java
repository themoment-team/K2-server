package com.moment.the.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AnswerUpdateDto {
    private Long AnswerIdx;
    private String contents;
}
