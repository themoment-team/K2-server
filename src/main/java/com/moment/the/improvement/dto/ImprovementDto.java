package com.moment.the.improvement.dto;

import com.moment.the.admin.AdminDomain;
import com.moment.the.improvement.ImprovementDomain;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import javax.validation.constraints.NotBlank;

public class ImprovementDto {

    @Getter @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
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

    @Getter
    @NoArgsConstructor
    public static class Response {
        @NotBlank
        private Long improveIdx;
        @NotBlank
        private String title;
        @NotBlank
        private String content;

        @QueryProjection
        public Response(Long improveIdx, String title, String content) {
            this.improveIdx = improveIdx;
            this.title = title;
            this.content = content;
        }
    }
}
