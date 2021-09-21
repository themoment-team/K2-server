package com.moment.the.improvement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.moment.the.admin.AdminDomain;
import com.moment.the.improvement.dto.ImprovementDto;
import lombok.*;

import javax.persistence.*;

@Entity @Table(name = "improvement")
@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor
public class ImprovementDomain {

    @JsonIgnore
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "improve_id")
    private Long improveIdx;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    // 쿼리한번으로 improvement 정보만 가져오는 방법.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private AdminDomain adminDomain;

    // dirty checking.
    public void update(ImprovementDto improvementDto) {
        this.title = improvementDto.getTitle();
        this.content = improvementDto.getContent();
    }
}
