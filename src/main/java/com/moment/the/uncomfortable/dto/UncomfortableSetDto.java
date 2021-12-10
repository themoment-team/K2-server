package com.moment.the.uncomfortable.dto;

import com.moment.the.uncomfortable.UncomfortableDomain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UncomfortableSetDto {

    @NotBlank(message = "content는 null, blank, empty를 허용하지 않습니다.")
    private String content;

    public UncomfortableDomain toEntity(){
        return UncomfortableDomain.builder()
                .content(this.content)
                .build();
    }
}
