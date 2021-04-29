package com.moment.the.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableViewAllDto {
    @NotBlank
    private Long boardIdx;

    @NotBlank
    private String content;
}
