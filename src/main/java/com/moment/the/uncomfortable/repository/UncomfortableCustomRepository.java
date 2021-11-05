package com.moment.the.uncomfortable.repository;

import com.moment.the.uncomfortable.dto.UncomfortableResponseDto;

import java.util.List;

/**
 * querydsl를 사용하기 위한 UncomfortableRepository의 CustomRepository
 * @author 정시원
 * @since 1.1.2
 * @version 1.1.2
 */
public interface UncomfortableCustomRepository {

    /**
     * UncomfortableDomain를 모두 조회하여 {@link UncomfortableResponseDto}로 변환하여 반환합니다.
     * @return List&#60;UncomfortableResponseDto&#62; UncomfortableDomain를 UncomfortableResponseDto로 변환한 리스트
     * @author 정시원
     */
    List<UncomfortableResponseDto> uncomfortableViewAll();

    /**
     * 불편한순간(Uncomfortable)에 좋아요 개수가 많은 순으로 정렬해 limit개의 결과를 반환합니다.
     * @param limit 조회결과 제한 개수
     * @return List&#60;UncomfortableResponseDto&#62; - 불편한순간를 모두 조회하여 나온 View전용 List
     * @author 정시원
     */
    List<UncomfortableResponseDto> uncomfortableViewTopBy(int limit);

    /**
     * 불편한순간에 좋아요를 모두 0으로 초기화 합니다.
     * @return long - 수정 된 게시글 수
     * @author 전지환
     */
    long formatAllGoods();
}
