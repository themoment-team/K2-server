package com.moment.the.controller;

import com.moment.the.domain.TableDomain;
import com.moment.the.dto.TableDto;
import com.moment.the.service.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class TableController {
    private final TableService tableService;
    // localhost:8080/v1/uncomfortable
    @PostMapping("/uncomfortable")
    public void write(@RequestBody TableDto tableDto){
        tableService.write(tableDto);
    }
    // localhost:8080/v1/uncomfortable
    @GetMapping("/uncomfortable")
    public List<TableDomain> top10(){
        return tableService.view();
    }
    // localhost:8080/v1/uncomfortable/{boardIdx}
    @PutMapping("/uncomfortable/{boardIdx}")
    public void goods(@PathVariable Long boardIdx){
        tableService.goods(boardIdx);
    }
    // localhost:8080/v1/uncomfortable/cancle/{boardIdx}
    @PutMapping("/uncomfortable/cancel/{boardIdx}")
    public void cancelGood(@PathVariable Long boardIdx){
        tableService.cancelGood(boardIdx);
    }
}
