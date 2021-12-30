package com.moment.the.answer.repository;

import com.moment.the.answer.dto.AnswerResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.moment.the.answer.QAnswerDomain.answerDomain;
import static com.moment.the.admin.QAdminDomain.adminDomain;

/**
 * querydsl를 사용하기 위한 AnswerCustomRepository의 CustomRepository구현체
 *
 * @author 정시원
 * @since 1,3,1
 * @version 1.3,1
 */
@RequiredArgsConstructor
public class AnswerCustomRepositoryImpl implements AnswerCustomRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public AnswerResponseDto findByUncomfortableIdx(long uncomfortableIdx) {
        return queryFactory
                .select(Projections.constructor(AnswerResponseDto.class,
                        answerDomain.answerIdx,
                        answerDomain.uncomfortableDomain.content.as("title"),
                        answerDomain.content,
                        adminDomain.name.as("writer")
                ))
                .from(answerDomain)
                .where(answerDomain.uncomfortableDomain.uncomfortableIdx.eq(uncomfortableIdx))
                .fetchOne();
    }
}
