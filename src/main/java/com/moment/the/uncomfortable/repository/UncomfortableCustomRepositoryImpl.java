package com.moment.the.uncomfortable.repository;

import com.moment.the.uncomfortable.dto.UncomfortableResponseDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

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
     * 불편한순간(uncomfortable)를 모두 조회하여 {@link UncomfortableResponseDto}로 변환하여 반환합니다.
     * TODO 기존 JPQL를 querydsl로 이전하기
     *     @Query("SELECT new com.moment.the.uncomfortable.dto.UncomfortableResponseDto(table.uncomfortableIdx, table.content, table.goods, answer)" +
     *             "FROM UncomfortableDomain table LEFT JOIN table.answerDomain answer " +
     *             "ORDER BY table.uncomfortableIdx DESC "
     *     )
     *     List<UncomfortableResponseDto> uncomfortableViewAll();
     * @return List&#60;UncomfortableResponseDto&#62; - 불편한순간를 모두 조회하여 나온 View전용 List
     * @author 정시원
     */
    @Override
    public List<UncomfortableResponseDto> uncomfortableViewAll() {
        return null;
    }

    /**
     * 불편한순간(Uncomfortable)에 좋아요 개수가 많은 순으로 정렬해 limit개의 결과를 반환합니다.
     * TODO 기존 JPQL를 querydsl로 이전하기
     *     @Query("SELECT new com.moment.the.uncomfortable.dto.UncomfortableResponseDto(table.uncomfortableIdx, table.content, table.goods, answer)" +
     *             "FROM UncomfortableDomain table LEFT JOIN table.answerDomain answer " +
     *             "ORDER BY table.goods DESC "
     *     )
     * @param limit 조회결과 제한 개수
     * @return List&#60;UncomfortableResponseDto&#62; - 불편한순간를 랭크순으로 조회하여 나온 View전용 List
     * @author 정시원
     */
    @Override
    public List<UncomfortableResponseDto> uncomfortableViewTopBy(int limit) {
        return null;
    }
}
