package com.moment.the.repository;

import com.moment.the.domain.AnswerDomain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<AnswerDomain, Long> {

    List<AnswerDomain> findAllBy();

}
