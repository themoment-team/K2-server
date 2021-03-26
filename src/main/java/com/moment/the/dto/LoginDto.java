package com.moment.the.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginDto {
    private String adminId;
    private String password;
}
