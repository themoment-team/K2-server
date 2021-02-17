package com.moment.the.service;

import com.moment.the.dto.AnswerDto;
import com.moment.the.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerService {

    final private AnswerRepository answerRepo;

    public void save(AnswerDto answerDto){
        answerRepo.save(answerDto.toEntity());
    }
}
