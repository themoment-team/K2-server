package com.moment.the.improvement.dto;

import com.moment.the.admin.AdminDomain;
import com.moment.the.improvement.ImprovementDomain;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import javax.validation.constraints.NotBlank;

public class ImprovementDto {

    @NoArgsConstructor
    public static class Request {
        @NotBlank
        private String title;
        @NotBlank
        private String content;

        public ImprovementDomain toEntity(AdminDomain adminDomain){
            return ImprovementDomain.builder()
                    .title(this.title)
                    .content(this.content)
                    .adminDomain(adminDomain)
                    .build();
        }
    }

    @NoArgsConstructor
    public static class Response {
        @NotBlank
        private String improveIdx;
        @NotBlank
        private String title;
        @NotBlank
        private String content;

        @QueryProjection
        public Response(String improveIdx, String title, String content) {
            this.improveIdx = improveIdx;
            this.title = title;
            this.content = content;
        }
    }
}
