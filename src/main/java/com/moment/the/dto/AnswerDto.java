package com.moment.the.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDto {

    private Long tableIdx;
    private String content;

}