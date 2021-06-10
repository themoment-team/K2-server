package com.moment.the.service;

import com.moment.the.domain.TableDomain;
import com.moment.the.dto.TableDto;
import com.moment.the.dto.TableViewDto;
import com.moment.the.repository.TableRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        // then
        assertEquals(tableDto.getContent(), savedTable.getContent());
    }

    @Test
    @DisplayName("Table top30 보여주기 테스트")
    void Table_top30_view_검증(){
        // Given
        AtomicInteger i = new AtomicInteger(1);
        List<TableDomain> TableDomains = Stream.generate(
                () -> TableDomain.builder()
                        .goods(i.getAndIncrement())
                        .content("고양이는 귀여워")
                        .build()
        ).limit(40).collect(Collectors.toList());

        // When
        tableRepo.saveAll(TableDomains);
        List<TableViewDto> viewTop30 = tableService.view();

        // Then
        assertEquals(viewTop30.size(), 30);
        AtomicInteger j = new AtomicInteger(40);
        Stream<TableViewDto> streamViewTop30 = viewTop30.stream();
        for(TableViewDto v : viewTop30 ) {
            assertEquals(v.getGoods(), j.getAndDecrement());
        }
        assertEquals(j.get(), 10);
    }

}