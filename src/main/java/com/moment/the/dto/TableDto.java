package com.moment.the.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableDto {
    @JsonIgnore
    private Long boardIdx;
    @NotNull
    private String content;
    @JsonIgnore
    private int goods;
}
