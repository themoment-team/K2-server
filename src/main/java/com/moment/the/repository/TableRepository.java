package com.moment.the.repository;

import com.moment.the.domain.TableDomain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TableRepository extends JpaRepository<TableDomain, Long>{
    Optional<TableDomain> findByBoardIdx(Long boardIdx);
}
