package com.moment.the.uncomfortable.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.moment.the.uncomfortable.repository.UncomfortableCustomRepositoryImpl;
import lombok.*;

/**
 * 사용자에게 전달되는 Response용 DTO이다.<br>
 * {@link isAnswer}는 APi에 따라서 불필요할 수 있다. (ex. 불편한순간 전체보기API)
 * @since 1.0.0
 * @author 정시원
 */
@Builder @Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) //null인 필드를 제외합니다.
public class UncomfortableResponseDto {

    private Long uncomfortableIdx;
    private String content;
    private int goods;

    private Boolean isAnswer; // rank API에서만 사용한다.

    /**
     * {@link UncomfortableCustomRepositoryImpl#uncomfortableViewAll()}에서 생성자 Projection에 필요하다.
     * @param uncomfortableIdx UncomfortableDomain의 idx
     * @param content UncomfortableDomain의 내용
     * @param goods UncomfortableDomain의 좋아요 개수
     * @see UncomfortableCustomRepositoryImpl#uncomfortableViewAll()
     * @author 정시원
     */
    public UncomfortableResponseDto(Long uncomfortableIdx, String content, int goods){
        this.uncomfortableIdx = uncomfortableIdx;
        this.content = content;
        this.goods = goods;
    }
}
