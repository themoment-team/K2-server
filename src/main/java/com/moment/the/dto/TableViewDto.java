package com.moment.the.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.moment.the.domain.AnswerDomain;
import lombok.*;

@Builder
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class TableViewDto {

    private Long boardIdx;
    private String content;
    private int goods;
    private boolean isAnswer;

    public TableViewDto(Long boardIdx, String content, int goods, AnswerDomain answer){
        this.boardIdx = boardIdx;
        this.content = content;
        this.goods = goods;
        this.isAnswer = answer != null;
    }

}
