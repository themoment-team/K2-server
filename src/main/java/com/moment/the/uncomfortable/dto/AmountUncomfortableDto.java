package com.moment.the.uncomfortable.dto;


import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AmountUncomfortableDto {
    @NotBlank
    private Long uncomfortableIdx;
}
