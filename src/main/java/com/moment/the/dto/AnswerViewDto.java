package com.moment.the.dto;

import com.moment.the.domain.AdminDomain;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnswerViewDto {
    @NotBlank
    private AdminDomain adminDomain;

    @NotBlank
    private String answerContent;
}
