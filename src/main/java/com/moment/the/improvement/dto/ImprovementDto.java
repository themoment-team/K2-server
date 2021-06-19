package com.moment.the.improvement.dto;

import com.moment.the.admin.AdminDomain;
import com.moment.the.improvement.ImprovementDomain;
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
