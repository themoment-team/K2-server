package com.moment.the.service;

import com.moment.the.advice.exception.GoodsNotCancelException;
import com.moment.the.advice.exception.NoPostException;
import com.moment.the.domain.TableDomain;
import com.moment.the.dto.TableDto;
import com.moment.the.dto.TableViewAllDto;
import com.moment.the.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<TableDomain> view() {
        return tableRepository.findTop10ByOrderByGoodsDesc();
    }

    // 전체 페이지 보여주기.
    public List<TableViewAllDto> viewAll(){
        ModelMapper modelMapper = new ModelMapper();
        return tableRepository.findAllByOrderByBoardIdxDesc().stream()
                .map(m -> modelMapper.map(m, TableViewAllDto.class))
                .collect(Collectors.toList());
    }

    // 좋아요 수 증가.
    @Transactional
    public void goods(Long boardIdx){
        TableDomain tableDomain = tableRepository.findByBoardIdx(boardIdx).orElseThrow(NoPostException::new);
        tableDomain.setGoods(tableDomain.getGoods()+1);
    }

    // 좋아요 수 감소.
    @Transactional
    public void cancelGood(Long boardIdx){
        TableDomain tableDomain = tableRepository.findByBoardIdx(boardIdx).orElseThrow(NoPostException::new);
      
        if(tableDomain.getGoods() - 1 <= 0) //좋야요가 음수가 되면
            throw new GoodsNotCancelException();

        tableDomain.setGoods(tableDomain.getGoods() - 1);
    }
}
