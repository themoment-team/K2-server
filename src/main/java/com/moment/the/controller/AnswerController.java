package com.moment.the.controller;

import com.moment.the.dto.AnswerDto;
import com.moment.the.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/top10")
@RequiredArgsConstructor
public class AnswerController {

    final private AnswerService answerService;

    @PostMapping("/answer")
    public void save(AnswerDto answerDto){
        answerService.save(answerDto);
    }
}
