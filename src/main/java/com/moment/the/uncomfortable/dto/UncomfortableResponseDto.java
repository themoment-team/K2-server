package com.moment.the.uncomfortable.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Builder @Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) //null인 필드를 제외합니다.
public class UncomfortableResponseDto {

    private Long uncomfortableIdx;
    private String content;
    private int goods;

    private Boolean isAnswer; // rank API에서만 사용한다.

    public UncomfortableResponseDto(Long uncomfortableIdx, String content, int goods){
        this.uncomfortableIdx = uncomfortableIdx;
        this.content = content;
        this.goods = goods;
    }
}
