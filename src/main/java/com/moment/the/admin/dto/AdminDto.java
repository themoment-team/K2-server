package com.moment.the.admin.dto;


import com.moment.the.admin.AdminDomain;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collections;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class AdminDto {
    @Email(message = "email should be valid")
    @NotBlank(message = "email should be valid")
    private String email;

    @NotBlank(message = "password should be valid")
    private String password;

    @NotBlank(message = "name should be valid")
    @Size(min = 3, max = 30)
    private String name;

    public AdminDomain toEntity() {
        return AdminDomain.builder()
                .email(this.email)
                .password(this.password)
                .name(this.name)
                .roles(Collections.singletonList("ROLE_ADMIN"))
                .build();
    }
}
