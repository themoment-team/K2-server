package com.moment.the.controller;

import com.moment.the.domain.AnswerDomain;
import com.moment.the.domain.TableDomain;
import com.moment.the.dto.TableDto;
import com.moment.the.service.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/uncomfortable")
public class TableController {
    private final TableService tableService;
    // localhost:8080/v1/uncomfortable/write
    @PostMapping("/write")
    public void write(@RequestBody TableDto tableDto){
        tableService.write(tableDto);
    }
    // localhost:8080/v1/uncomfortable/top10
    @GetMapping("/top10")
    public List<TableDomain> top10(){
        return tableService.view();
    }
    // localhost:8080/v1/uncomfortable/good/{boardIdx}
    @PutMapping("/good/{boardIdx}")
    public void goods(@PathVariable Long boardIdx){
        tableService.goods(boardIdx);
    }
    // localhost:8080/v1/uncomfortable/good/cancle/{boardIdx}
    @PutMapping("/good/cancel/{boardIdx}")
    public void cancelGood(@PathVariable Long boardIdx){
        tableService.cancelGood(boardIdx);
    }
    //하려다가 실패한것
//
//    @GetMapping("/")
//    public List<AnswerDomain> findAll(){
//        return tableService.findAll();
//    }
}
