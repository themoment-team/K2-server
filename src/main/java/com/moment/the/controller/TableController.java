package com.moment.the.controller;

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
    @GetMapping("/top10")
    public List<TableDomain> top10(){
        return tableService.view();
    }
}
