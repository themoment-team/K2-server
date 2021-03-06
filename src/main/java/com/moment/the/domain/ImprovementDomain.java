package com.moment.the.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.moment.the.dto.ImprovementDto;
import lombok.*;

import javax.persistence.*;

@Table(name = "Improvement")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
// No setter!!
public class ImprovementDomain {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long improveIdx;
    //Needs to adminIdx FK Mapping.
    @Column
    private String improveHeader;
    @Column
    private String improveContent;
    // dirty checking.
    public void update(ImprovementDto improvementDto) {
        this.improveHeader = improvementDto.getImproveHeader();
        this.improveContent = improvementDto.getImproveContent();
    }
}
