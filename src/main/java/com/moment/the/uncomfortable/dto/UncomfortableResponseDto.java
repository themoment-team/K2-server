package com.moment.the.uncomfortable.dto;

import lombok.*;

@Builder
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class UncomfortableResponseDto {

    private Long boardIdx;
    private String content;
    private int goods;
    private boolean isAnswer;
}
