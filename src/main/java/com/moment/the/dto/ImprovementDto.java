package com.moment.the.dto;

import com.moment.the.domain.AdminDomain;
import com.moment.the.domain.ImprovementDomain;
import lombok.*;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImprovementDto {
    @NotBlank
    private String improveHeader;
    @NotBlank
    private String improveContent;

    public ImprovementDomain ToEntity(AdminDomain adminDomain){
        return ImprovementDomain.builder()
                .improveHeader(this.getImproveHeader())
                .improveContent(this.getImproveContent())
                .adminDomain(adminDomain)
                .build();
    }
}
