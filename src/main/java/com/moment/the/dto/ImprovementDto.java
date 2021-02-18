package com.moment.the.dto;

import com.sun.istack.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImprovementDto {
    @NotNull
    private String improveHeader;
    @NotNull
    private String improveContent;
}
