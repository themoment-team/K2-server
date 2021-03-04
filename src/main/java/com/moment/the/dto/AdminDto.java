package com.moment.the.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminDto {
    @Email
    @NotNull
    private String adminId;

    @NotNull
    private String adminPwd;

    @NotNull
    @Size(min = 3, max = 30)
    private String adminName;
}
