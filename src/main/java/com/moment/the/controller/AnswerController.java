package com.moment.the.controller;

import com.moment.the.dto.AnswerDto;
import com.moment.the.dto.AnswerUpdateDto;
import com.moment.the.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/top10")
@RequiredArgsConstructor
public class AnswerController {
    private final AnswerService answerService;

    @PostMapping("/answer")
    public void save(@RequestBody AnswerDto answerDto){
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
