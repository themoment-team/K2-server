package com.moment.the.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
public class SignInDto {
    @NotBlank(message = "id should be valid")
    private String adminId;
    @NotBlank(message = "password should be valid")
    private String adminPwd;
}
