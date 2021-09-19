package com.moment.the.improvement.dto;

import com.moment.the.admin.AdminDomain;
import com.moment.the.improvement.ImprovementDomain;
import lombok.*;

import javax.validation.constraints.NotBlank;


@Getter @Setter
@Builder
@NoArgsConstructor @AllArgsConstructor
public class ImprovementDto {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    public ImprovementDomain toEntity(AdminDomain adminDomain){
        return ImprovementDomain.builder()
                .title(this.title)
                .content(this.content)
                .adminDomain(adminDomain)
                .build();
    }
}
