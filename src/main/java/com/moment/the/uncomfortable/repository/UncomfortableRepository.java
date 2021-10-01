package com.moment.the.uncomfortable.repository;

import com.moment.the.uncomfortable.UncomfortableDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UncomfortableRepository extends JpaRepository<UncomfortableDomain, Long>, UncomfortableCustomRepository{

    Optional<UncomfortableDomain> findByUncomfortableIdx(Long UncomfortableIdx);
}
