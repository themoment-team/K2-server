package com.moment.the.uncomfortable.repository;

import com.moment.the.uncomfortable.dto.UncomfortableResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.moment.the.uncomfortable.QUncomfortableDomain.uncomfortableDomain;

/**
 * querydsl를 사용하기 위한 UncomfortableRepository의 CustomRepository구현체
 * @author 정시원
 * @since 1.1.2
 * @version 1.1.2
 */
@RequiredArgsConstructor
public class UncomfortableCustomRepositoryImpl implements UncomfortableCustomRepository {

    private final JPAQueryFactory queryFactory;

    /**
     * UncomfortableDomain를 모두 조회하여 {@link UncomfortableResponseDto}로 변환하여 반환합니다.
     * @return List&#60;UncomfortableResponseDto&#62; UncomfortableDomain를 UncomfortableResponseDto로 변환한 리스트
     * @author 정시원
     */
    @Override
    public List<UncomfortableResponseDto> uncomfortableViewAll() {
        return queryFactory
                .from(uncomfortableDomain)
                .select(Projections.constructor(UncomfortableResponseDto.class, // 생성자를 통해 DTO로 select한다.
                    uncomfortableDomain.uncomfortableIdx,
                    uncomfortableDomain.content,
                    uncomfortableDomain.goods
                )
        ).fetch();
    }

    /**
     * 불편한순간(Uncomfortable)에 좋아요 개수가 많은 순으로 정렬해 limit개의 결과를 반환합니다.
     * @param limit 조회결과 제한 개수
     * @return List&#60;UncomfortableResponseDto&#62; - 불편한순간를 랭크순으로 조회하여 나온 View전용 List
     * @author 정시원
     */
    @Override
    public List<UncomfortableResponseDto> uncomfortableViewTopBy(int limit) {
        return queryFactory
                .from(uncomfortableDomain)
                .select(Projections.constructor(UncomfortableResponseDto.class,
                        uncomfortableDomain.uncomfortableIdx,
                        uncomfortableDomain.content,
                        uncomfortableDomain.goods,
                        uncomfortableDomain.answerDomain.isNotNull()
                ))
                .limit(limit)
                .orderBy(uncomfortableDomain.goods.desc())
                .fetch();
    }

    /**
     * 불편한순간에 좋아요를 모두 0으로 초기화 합니다.
     * @return long - 수정 된 게시글 수
     * @author 전지환
     */
    @Override
    public long formatAllGoods() {
        return queryFactory
                .update(uncomfortableDomain)
                .where(uncomfortableDomain.goods.eq(0).not())
                .set(uncomfortableDomain.goods, 0)
                .execute();
    }
}
