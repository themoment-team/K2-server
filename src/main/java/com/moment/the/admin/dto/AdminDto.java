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
    @Email(message = "@를 포함 이메일 형식을 갖춰야 합니다.")
    @NotBlank(message = "null과 공백을 허용하지 않습니다.")
    private String email;

    @NotBlank(message = "null과 공백을 허용하지 않습니다.")
    private String password;

    @NotBlank(message = "null과 공백을 허용하지 않습니다.")
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
