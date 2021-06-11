package com.moment.the.dto;

import com.moment.the.domain.AdminDomain;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignInDto {
    @NotBlank(message = "id should be valid")
    private String adminId;
    @NotBlank(message = "password should be valid")
    private String adminPwd;

    private AdminDomain toEntity(){
        return AdminDomain.builder()
                .adminId(this.adminId)
                .adminPwd(this.adminPwd)
                .build();
    }
}
