package com.moment.the.uncomfortable.repository;

import com.moment.the.uncomfortable.UncomfortableEntity;
import com.moment.the.uncomfortable.dto.UncomfortableResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UncomfortableRepository extends JpaRepository<UncomfortableEntity, Long>{

    Optional<UncomfortableEntity> findByBoardIdx(Long boardIdx);

    @Query(value = "SELECT COUNT(table.boardIdx) " +
            "FROM UncomfortableEntity table" )
    Long amountUncomfortable();

    @Query("SELECT new com.moment.the.uncomfortable.dto.UncomfortableResponseDto(table.boardIdx, table.content, table.goods, answer)" +
            "FROM UncomfortableEntity table LEFT JOIN table.answerDomain answer " +
            "ORDER BY table.boardIdx DESC "
    )
    List<UncomfortableResponseDto> uncomfortableViewAll();

    @Query("SELECT new com.moment.the.uncomfortable.dto.UncomfortableResponseDto(table.boardIdx, table.content, table.goods, answer)" +
            "FROM UncomfortableEntity table LEFT JOIN table.answerDomain answer " +
            "ORDER BY table.goods DESC "
    )
    List<UncomfortableResponseDto> uncomfortableViewTopBy(Pageable p);
}
