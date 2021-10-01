package com.moment.the.uncomfortable.repository;

import com.moment.the.uncomfortable.dto.UncomfortableResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
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
}
