package com.moment.the.service;

import com.moment.the.domain.Table;
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
    public Table write(TableDto tableDto){
        Table table = Table.builder()
                .content(tableDto.getContent())
                .build();
        return tableRepository.save(table);
    }
}
