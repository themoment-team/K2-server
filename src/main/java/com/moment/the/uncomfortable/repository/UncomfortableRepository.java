package com.moment.the.uncomfortable.repository;

import com.moment.the.uncomfortable.UncomfortableDomain;
import com.moment.the.uncomfortable.dto.UncomfortableResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UncomfortableRepository extends JpaRepository<UncomfortableDomain, Long>, UncomfortableCustomRepository{

    Optional<UncomfortableDomain> findByUncomfortableIdx(Long UncomfortableIdx);

    @Query("SELECT new com.moment.the.uncomfortable.dto.UncomfortableResponseDto(table.uncomfortableIdx, table.content, table.goods, answer)" +
            "FROM UncomfortableDomain table LEFT JOIN table.answerDomain answer " +
            "ORDER BY table.uncomfortableIdx DESC "
    )
    List<UncomfortableResponseDto> uncomfortableViewAll();

    @Query("SELECT new com.moment.the.uncomfortable.dto.UncomfortableResponseDto(table.uncomfortableIdx, table.content, table.goods, answer)" +
            "FROM UncomfortableDomain table LEFT JOIN table.answerDomain answer " +
            "ORDER BY table.goods DESC "
    )
    List<UncomfortableResponseDto> uncomfortableViewTopBy(Pageable p);
}
