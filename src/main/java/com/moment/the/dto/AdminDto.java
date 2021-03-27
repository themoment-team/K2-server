package com.moment.the.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.moment.the.domain.AdminDomain;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminDto {
    @Email(message = "Email should be valid")
    @NotNull
    private String adminId;

    @NotNull
    private String adminPwd;

    @NotNull
    @Size(min = 3, max = 30)
    private String adminName;

    @JsonIgnore
    private String adminAuth;

    public AdminDomain toEntity() {
        return AdminDomain.builder()
                .adminId(this.getAdminId())
                .adminPwd(this.getAdminPwd())
                .adminName(this.getAdminName())
                .build();
    }
}
