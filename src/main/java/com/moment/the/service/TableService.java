package com.moment.the.service;

import com.moment.the.advice.exception.GoodsNotCancelException;
import com.moment.the.advice.exception.NoPostException;
import com.moment.the.domain.TableDomain;
import com.moment.the.dto.AmountUncomfortableDto;
import com.moment.the.dto.TableDto;
import com.moment.the.dto.TableViewDto;
import com.moment.the.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
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

    // Top 30 보여주기.
    public List<TableViewDto> view() {
        return tableRepository.tableViewTopBy(PageRequest.of(0,30));
    }

    // 전체 페이지 보여주기.
    public List<TableViewDto> viewAll(){
        return tableRepository.tableViewAll();
    }

    // 전체 게시물 개수 보여주기.
    public Long amountUncomfortableView(){
        return tableRepository.amountUncomfortable();
    }

    // 프로젝트 시작 이후 날짜 보여주기.
    public Integer dateSinceProjectStart(){
        LocalDate currentDate = LocalDate.now();
        return calculateAfterDate(currentDate);
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

    // day 수 계산하기
    public static Integer calculateAfterDate(LocalDate todayDate){
        // the_moment 프로젝트 시작 날짜
        LocalDate startTheMoment = LocalDate.of(2021,6,7);

        // the_moment 프로젝트를 시작한 날짜 by 오늘의 날짜
        Period period = startTheMoment.until(todayDate);

        // +1 을 해야 d-day 부터 1일차로 계산
        return period.getDays()+1;
    }
}
