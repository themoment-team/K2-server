package com.moment.the.repository;

import com.moment.the.domain.TableDomain;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TableRepository extends JpaRepository<TableDomain, Long>{
    // idx로 table 찾기.
    Optional<TableDomain> findByBoardIdx(Long boardIdx);
    // Goods 수 내림차순 정렬.
    List<TableDomain> findAllByOrderByGoodsDesc();
}
