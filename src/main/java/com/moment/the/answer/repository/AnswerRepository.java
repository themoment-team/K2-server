package com.moment.the.answer.repository;


import com.moment.the.admin.AdminDomain;
import com.moment.the.answer.AnswerDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<AnswerDomain, Long> {
    void deleteAllByAnswerIdx(Long answerIdx);

    Optional<AnswerDomain> findByAdminDomain(AdminDomain adminDomain);

    AnswerDomain findTop1ByUncomfortableDomain_uncomfortableIdx(Long uncomfortableIdx);
}
