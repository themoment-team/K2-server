package com.moment.the.dto;


import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AmountUncomfortableDto {
    @NotBlank
    private Long boardIdx;
}
