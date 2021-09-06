package com.moment.the.uncomfortable.dto;

import com.moment.the.uncomfortable.UncomfortableEntity;
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

    public UncomfortableEntity toEntity(){
        return UncomfortableEntity.builder()
                .content(this.content)
                .build();
    }
}
