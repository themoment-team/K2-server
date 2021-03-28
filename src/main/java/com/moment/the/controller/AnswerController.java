package com.moment.the.controller;

import com.moment.the.dto.AnswerDto;
import com.moment.the.dto.AnswerUpdateDto;
import com.moment.the.service.AnswerService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/top10")
@RequiredArgsConstructor
public class AnswerController {
    private final AnswerService answerService;

    @PostMapping("/answer")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    public void save(@RequestBody AnswerDto answerDto) throws Exception {
        answerService.save(answerDto);
    }

    @PutMapping("/answer")
    public void update(@RequestBody AnswerUpdateDto answerUpdateDto){
        answerService.update(answerUpdateDto);
    }

    @DeleteMapping("/answer/{answerIdx}")
    public void delete(@PathVariable Long answerIdx){
        answerService.delete(answerIdx);
    }
}
