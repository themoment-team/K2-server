package com.moment.the.service;

import com.moment.the.domain.TableDomain;
import com.moment.the.dto.TableDto;
import com.moment.the.repository.TableRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TableServiceTest {

    @Autowired TableRepository tableRepo;
    @Autowired TableService tableService;

    @Test
    @DisplayName("Table write 로직 검증")
    void write_로직검증(){
        // given
        TableDto tableDto = TableDto.builder()
                .content("고양이는 귀여워요")
                .build();

        // when
        TableDomain writeTable = tableService.write(tableDto);
        tableRepo.flush();
        TableDomain savedTable = tableRepo.findByBoardIdx(writeTable.getBoardIdx()).orElseThrow(() -> new IllegalArgumentException("Table을 찾을 수 없습니다. (테스트실패)"));

        // than
        assertEquals(tableDto.getContent(), savedTable.getContent());
    }


}