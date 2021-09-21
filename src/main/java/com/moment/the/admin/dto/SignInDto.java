package com.moment.the.admin.dto;

import com.moment.the.admin.AdminDomain;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignInDto {
    @NotBlank(message = "id should be valid")
    private String email;
    @NotBlank(message = "password should be valid")
    private String password;

    public AdminDomain toEntity(){
        return AdminDomain.builder()
                .email(this.email)
                .password(this.password)
                .build();
    }
}
