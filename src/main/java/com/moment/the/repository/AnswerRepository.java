package com.moment.the.repository;

import com.moment.the.domain.AnswerDomain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<AnswerDomain, Long> { }
