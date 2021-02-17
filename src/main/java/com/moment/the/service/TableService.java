package com.moment.the.service;

import com.moment.the.domain.TableDomain;
import com.moment.the.dto.TableDto;
import com.moment.the.repository.TableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TableService {
    private final TableRepository tableRepository;

    public TableService(TableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    @Transactional
    public TableDomain write(TableDto tableDto){
        TableDomain table = TableDomain.builder()
                .content(tableDto.getContent())
                .build();
        return tableRepository.save(table);
    }
}
