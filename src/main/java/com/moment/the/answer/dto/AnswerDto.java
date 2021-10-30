package com.moment.the.answer.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.moment.the.admin.AdminDomain;
import com.moment.the.answer.AnswerDomain;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor @AllArgsConstructor
public class AnswerDto {
    private String content;

    @JsonIgnore
    private AdminDomain adminDomain;

    public void setAdminDomain(AdminDomain adminDomain) {
        this.adminDomain = adminDomain;
    }

    public AnswerDomain toEntity(){
        return AnswerDomain.builder()
                .content(this.content)
                .adminDomain(this.adminDomain)
                .build();
    }
}