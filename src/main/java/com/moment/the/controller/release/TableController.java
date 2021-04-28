package com.moment.the.controller.release;

import com.moment.the.domain.TableDomain;
import com.moment.the.domain.response.CommonResult;
import com.moment.the.domain.response.ListResult;
import com.moment.the.domain.response.ResponseService;
import com.moment.the.dto.TableDto;
import com.moment.the.service.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class TableController {
    //Dependency Injection
    private final TableService tableService;
    private final ResponseService responseService;

    // localhost:8080/v1/uncomfortable
    @PostMapping("/uncomfortable")
    public CommonResult write(@Valid @RequestBody TableDto tableDto){
        tableService.write(tableDto);
        return responseService.getSuccessResult();
    }

    // localhost:8080/v1/uncomfortable/top10
    @GetMapping("/uncomfortable/top10")
    public ListResult<TableDomain> top10(){
        return responseService.getListResult(tableService.view());
    }

    // localhost:8080/v1/uncomfortable
    @GetMapping("/uncomfortable")
    public ListResult<TableDomain> viewAll(){
        return responseService.getListResult(tableService.viewAll());
    }

    // localhost:8080/v1/uncomfortable/{boardIdx}
    @PutMapping("/uncomfortable/{boardIdx}")
    public CommonResult goods(@PathVariable Long boardIdx){
        tableService.goods(boardIdx);
        return responseService.getSuccessResult();
    }

    // localhost:8080/v1/uncomfortable/cancel/{boardIdx}
    @PutMapping("/uncomfortable/cancel/{boardIdx}")
    public CommonResult cancelGood(@PathVariable Long boardIdx){
        tableService.cancelGood(boardIdx);
        return responseService.getSuccessResult();
    }
}
