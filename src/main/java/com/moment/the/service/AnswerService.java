package com.moment.the.service;

import com.moment.the.domain.TableDomain;
import com.moment.the.dto.AnswerDto;
import com.moment.the.repository.AnswerRepository;
import com.moment.the.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerService {

    final private AnswerRepository answerRepo;
    final private TableRepository tableRepo;

    public void save(AnswerDto answerDto){
        TableDomain table = findBy(answerDto.getTableIdx());
        answerDto.setTable(table);
        answerRepo.save(answerDto.toEntity());
    }

    //idx 로 table 찾는 매서드
    public TableDomain findBy(Long tableId){
        return tableRepo.findById(tableId).orElseThrow(() -> new IllegalArgumentException("해당 Table 은 없습니다."));
    }
}
