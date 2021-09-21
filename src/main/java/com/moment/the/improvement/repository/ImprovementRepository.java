package com.moment.the.improvement.repository;

import com.moment.the.improvement.ImprovementDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImprovementRepository extends JpaRepository<ImprovementDomain, Long> {
    // 해당 idx 찾기.
    ImprovementDomain findByImproveIdx(Long improveIdx);
    // 개시글 제목으로 찾기.
    ImprovementDomain findByContent(String content);
    // 해당 idx 삭제하기.
    void deleteAllByImproveIdx(Long improveIdx);
    // 모든 idx 최신순으로 조회하기.
    List<ImprovementDomain> findAllByOrderByImproveIdxDesc();
}