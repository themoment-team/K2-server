package com.moment.the.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthEditDto {
    @Email
    @NotNull
    private String existingEmail;

    @Email
    @NotNull
    private String editEmail;
}
