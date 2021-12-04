package com.moment.the.answer.repository;

import com.moment.the.answer.AnswerDomain;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.moment.the.answer.QAnswerDomain.answerDomain;

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
    public AnswerDomain findByUncomfortableIdx(long uncomfortableIdx) {
        return queryFactory
                .selectFrom(answerDomain)
                .where(answerDomain.uncomfortableDomain.uncomfortableIdx.eq(uncomfortableIdx))
                .fetchOne();
    }
}
