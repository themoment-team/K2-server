package com.moment.the.service;

import com.moment.the.domain.AnswerDomain;
import com.moment.the.domain.TableDomain;
import com.moment.the.dto.AnswerDto;
import com.moment.the.dto.AnswerUpdateDto;
import com.moment.the.repository.AnswerRepository;
import com.moment.the.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AnswerService {

    final private AnswerRepository answerRepo;
    final private TableRepository tableRepo;

    public void save(AnswerDto answerDto){
        TableDomain table = tableFindBy(answerDto.getTableIdx());
        answerDto.setTable(table);
        answerRepo.save(answerDto.toEntity());
    }

    @Transactional
    public void update(AnswerUpdateDto answerUpdateDto){
        AnswerDomain answer = answerFindBy(answerUpdateDto.getAnswerIdx());
        answer.update(answerUpdateDto.getContents());
    }

    public void delete(Long answerIdx){
        answerRepo.deleteById(answerIdx);
    }

    public AnswerDomain answerFindBy(Long answerId){
        return answerRepo.findById(answerId).orElseThrow(() -> new IllegalArgumentException("해당 답변은 없습니다."));
    }

    //idx 로 table 찾는 매서드
    public TableDomain tableFindBy(Long tableId){
        return tableRepo.findById(tableId).orElseThrow(() -> new IllegalArgumentException("해당 Table 은 없습니다."));
    }
}
