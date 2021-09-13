package com.moment.the.improvement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.moment.the.admin.AdminDomain;
import com.moment.the.improvement.dto.ImprovementDto;
import lombok.*;

import javax.persistence.*;

@Table(name = "Improvement")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ImprovementDomain {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long improveIdx;
    @Column
    private String improveHeader;
    @Column
    private String improveContent;

    // 쿼리한번으로 improvement 정보만 가져오는 방법.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="adminIdx")
    private AdminDomain adminDomain;

    // dirty checking.
    public void update(ImprovementDto improvementDto) {
        this.improveHeader = improvementDto.getImproveHeader();
        this.improveContent = improvementDto.getImproveContent();
    }
}
