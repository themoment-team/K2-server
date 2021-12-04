package com.moment.the.answer.repository;


import com.moment.the.answer.AnswerDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * AnswerDomain의 Repository
 *
 * @author 전지환, 정시원
 * @version 1.3.1
 * @since 1.0.0
 */
@Repository
public interface AnswerRepository extends JpaRepository<AnswerDomain, Long>, AnswerCustomRepository {
    void deleteAllByAnswerIdx(Long answerIdx);
}
