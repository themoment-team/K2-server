package com.moment.the.uncomfortable.repository;

import com.moment.the.uncomfortable.UncomfortableEntity;
import com.moment.the.uncomfortable.dto.UncomfortableGetDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UncomfortableRepository extends JpaRepository<UncomfortableEntity, Long>{
    // idx로 uncomfortable 찾기.
    Optional<UncomfortableEntity> findByUncomfortableIdx(Long uncomfortableIdx);

    @Query(value = "SELECT COUNT(table.uncomfortableIdx) " +
            "FROM UncomfortableEntity table" )
    Long amountUncomfortable();

    @Query("SELECT new com.moment.the.uncomfortable.dto.UncomfortableGetDto(table.uncomfortableIdx, table.content, table.goods, answer)" +
            "FROM UncomfortableEntity table LEFT JOIN table.answerDomain answer " +
            "ORDER BY table.uncomfortableIdx DESC "
    )
    List<UncomfortableGetDto> uncomfortableViewAll();

    @Query("SELECT new com.moment.the.uncomfortable.dto.UncomfortableGetDto(table.uncomfortableIdx, table.content, table.goods, answer)" +
            "FROM UncomfortableEntity table LEFT JOIN table.answerDomain answer " +
            "ORDER BY table.goods DESC "
    )
    List<UncomfortableGetDto> uncomfortableViewTopBy(Pageable p);
}
