package com.moment.the.dto;

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
    private String improveHeader;
    @NotBlank
    private String improveContent;
}
