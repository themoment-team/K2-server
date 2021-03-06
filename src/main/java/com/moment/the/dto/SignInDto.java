package com.moment.the.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignInDto {
    private String adminId;
    private String adminPwd;
}
