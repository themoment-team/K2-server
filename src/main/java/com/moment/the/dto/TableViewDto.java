package com.moment.the.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class TableViewDto {

    private Long boardIdx;
    private String content;
    private int goods;

    @JsonProperty("answer")
    private AnswerResDto answerResDto;


    public TableViewDto(Long boardIdx, String content, int goods, Long answerIdx, String answerContents, String adminName){
        this.boardIdx = boardIdx;
        this.content = content;
        this.goods = goods;
        if(answerIdx != null){
            this.answerResDto =
                    AnswerResDto.builder()
                            .answerIdx(answerIdx)
                            .content(answerContents)
                            .writer(adminName)
                            .build();
        }
    }
}
