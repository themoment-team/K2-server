package com.moment.the.improvement.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImprovementViewAllDto {
    @NotBlank
    private String improveIdx;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
}
