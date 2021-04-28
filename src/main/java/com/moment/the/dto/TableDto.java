package com.moment.the.dto;

import com.moment.the.domain.TableDomain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableDto {
    @NotBlank
    private String content;

    public TableDomain toEntity(String content){
        return TableDomain.builder()
                .content(content)
                .build();
    }
}
