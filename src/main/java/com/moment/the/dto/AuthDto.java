package com.moment.the.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthDto {
    @Email
    @NotNull
    private String authEmail;
}
