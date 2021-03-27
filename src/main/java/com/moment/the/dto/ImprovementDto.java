package com.moment.the.dto;

import com.moment.the.domain.AdminDomain;
import com.moment.the.domain.ImprovementDomain;
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

    public ImprovementDomain ToEntity(AdminDomain adminDomain){
        return ImprovementDomain.builder()
                .improveHeader(this.getImproveHeader())
                .improveContent(this.getImproveContent())
                .adminDomain(adminDomain)
                .build();
    }
}
