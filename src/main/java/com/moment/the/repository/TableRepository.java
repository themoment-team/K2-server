package com.moment.the.repository;

import com.moment.the.domain.Table;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TableRepository extends JpaRepository<Table, Long>{
    Optional<Table> findByBoardIdx(String boardIdx);
}
