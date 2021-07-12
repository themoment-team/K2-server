package com.moment.the.table.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TableServiceTest {

    @Autowired
    private TableService tableService;

    @Test @DisplayName("디데이 확인 가능?")
    public void 디데이_확인해봅시다() {
        //Given
        LocalDate nowDate = LocalDate.now();
        //When
        Integer result = TableService.calculateAfterDate(nowDate);
        //Then
        assertEquals(LocalDate.of(2021, 6, 7).until(nowDate).toString(), result);
    }
}