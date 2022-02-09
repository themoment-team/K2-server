package com.moment.the.meta.controller;

import com.moment.the.meta.service.MetaService;
import com.moment.the.response.ResponseService;
import com.moment.the.response.result.SingleResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/meta/v1.3.1/")
@RequiredArgsConstructor
public class MetaController {

    private final ResponseService responseService;
    private final MetaService metaService;

    @GetMapping("/everymoment-term")
    public SingleResult<Integer> getTermProjectStart(){
        return responseService.getSingleResult(metaService.getTermProjectStart());
    }

}
