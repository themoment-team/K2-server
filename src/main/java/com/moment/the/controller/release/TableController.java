package com.moment.the.controller.release;

import com.moment.the.domain.TableDomain;
import com.moment.the.dto.TableDto;
import com.moment.the.service.TableService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class TableController {
    //Dependency Injection
    private TableService tableService;
    public TableController(TableService tableService) {
        this.tableService = tableService;
    }
    // localhost:8080/v1/uncomfortable
    @PostMapping("/uncomfortable")
    public void write(@Valid @RequestBody TableDto tableDto){
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
    // localhost:8080/v1/uncomfortable/cancel/{boardIdx}
    @PutMapping("/uncomfortable/cancel/{boardIdx}")
    public void cancelGood(@PathVariable Long boardIdx){
        tableService.cancelGood(boardIdx);
    }
}
