package com.moment.the.answer.repository;

import com.moment.the.answer.AnswerDomain;

/**
 * querydsl를 사용하기 위한 AnswerCustomRepository의 CustomRepository구현체
 *
 * @author 정시원
 * @since 1,3,1
 * @version 1.3,1
 */
public interface AnswerCustomRepository {

    AnswerDomain findByUncomfortableIdx(long uncomfortableIdx);
}
