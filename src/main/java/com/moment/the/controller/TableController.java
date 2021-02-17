package com.moment.the.controller;

import com.moment.the.dto.TableDto;
import com.moment.the.service.TableService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/uncomfortable")
public class TableController {
    private final TableService tableService;

    public TableController(TableService tableService) {
        this.tableService = tableService;
    }
    // localhost:8080/v1/uncomfortable/write
    @PostMapping("/write")
    public void write(@RequestBody TableDto tableDto){
        tableService.write(tableDto);
    }
}
