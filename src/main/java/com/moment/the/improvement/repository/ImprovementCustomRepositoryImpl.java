package com.moment.the.improvement.repository;

import com.moment.the.improvement.dto.ImprovementDto;
import com.moment.the.improvement.dto.QImprovementDto_Response;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


import static com.moment.the.improvement.QImprovementDomain.improvementDomain;

/**
 * 실제개선사례 customRepository
 *
 * @version 1.3.1
 * @author 전지환
 */
@RequiredArgsConstructor
public class ImprovementCustomRepositoryImpl implements ImprovementCustomRepository{

    private final JPAQueryFactory queryFactory;

    /**
     * 실제개선사례 전체 조회 쿼리 메소드
     *
     * @return List<ImprovementDto.Response>
     * @author 전지환
     */
    @Override
    @Transactional(readOnly = true)
    public List<ImprovementDto.Response> getAllImprovement() {
        return queryFactory
                .select(new QImprovementDto_Response(
                        improvementDomain.improveIdx,
                        improvementDomain.title,
                        improvementDomain.content
                ))
                .from(improvementDomain)
                .fetch();

    }
}