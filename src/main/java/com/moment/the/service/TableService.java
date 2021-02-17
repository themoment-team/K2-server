package com.moment.the.service;

import com.moment.the.domain.TableDomain;
import com.moment.the.dto.TableDto;
import com.moment.the.repository.TableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TableService {
    private final TableRepository tableRepository;
    public TableService(TableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }
    // 작성하기.
    @Transactional
    public TableDomain write(TableDto tableDto){
        TableDomain table = TableDomain.builder()
                .content(tableDto.getContent())
                .build();
        return tableRepository.save(table);
    }
    // Top 10 보여주기.
    public List<TableDomain> view() {
        return tableRepository.findAllByOrderByGoodsDesc();
    }
    // 좋아요 수 증가.
    @Transactional
    public void goods(Long boardIdx){
        TableDomain tableDomain = tableRepository.findByBoardIdx(boardIdx).orElseThrow();
        tableDomain.setGoods(tableDomain.getGoods()+1);
    }
    // 좋아요 수 감소.
    @Transactional
    public void cancleGood(Long boardIdx){
        TableDomain tableDomain = tableRepository.findByBoardIdx(boardIdx).orElseThrow();
        tableDomain.setGoods(tableDomain.getGoods()-1);
    }
}
