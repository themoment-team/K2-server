package com.moment.the.service;

import com.moment.the.advice.exception.GoodsNotCancelException;
import com.moment.the.advice.exception.NoPostException;
import com.moment.the.domain.TableDomain;
import com.moment.the.dto.AmountUncomfortableDto;
import com.moment.the.dto.TableDto;
import com.moment.the.dto.TableViewDto;
import com.moment.the.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TableService {
    private final TableRepository tableRepository;

    // 작성하기.
    @Transactional
    public TableDomain write(TableDto tableDto){
        return tableRepository.save(tableDto.toEntity(tableDto.getContent()));
    }

    // Top 10 보여주기.
    public List<TableViewDto> view() {
        return tableRepository.tableViewTopBy(PageRequest.of(0,10));
    }

    // 전체 페이지 보여주기.
    public List<TableViewDto> viewAll(){
        return tableRepository.tableViewAll();
    }

    // 전체 게시물 개수 보여주기.
    public Long amountUncomfortableView(){
        return tableRepository.amountUncomfortable();
    }

    // 좋아요 수 증가.
    @Transactional
    public void goods(Long boardIdx){
        TableDomain tableDomain = tableRepository.findByBoardIdx(boardIdx).orElseThrow(NoPostException::new);
        tableDomain.updateGoods(tableDomain.getGoods()+1);
    }

    // 좋아요 수 감소.
    @Transactional
    public void cancelGood(Long boardIdx){
        TableDomain tableDomain = tableRepository.findByBoardIdx(boardIdx).orElseThrow(NoPostException::new);
      
        if(tableDomain.getGoods() - 1 <= 0) //좋야요가 음수가 되면
            throw new GoodsNotCancelException();

        tableDomain.updateGoods(tableDomain.getGoods() - 1);
    }
}
