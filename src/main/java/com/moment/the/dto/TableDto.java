package com.moment.the.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableDto {
    @JsonIgnore
    private Long boardIdx;
    @NotBlank
    private String content;
    @JsonIgnore
    private int goods;
}
