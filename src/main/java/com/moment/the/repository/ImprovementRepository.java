package com.moment.the.repository;

import com.moment.the.domain.ImprovementDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImprovementRepository extends JpaRepository<ImprovementDomain, Long> {
    // 해당 idx 찾기.
    ImprovementDomain findByImproveIdx(Long improveIdx);
    // 해당 idx 삭제하기.
    void deleteAllByImproveIdx(Long improveIdx);
}
