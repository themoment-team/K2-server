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
    @DisplayName("TableService write 로직 검증")
    void TableService_write_로직검증(){
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
    @DisplayName("TableService top30 보여주기 테스트")
    void TableService_top30_view_검증(){
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
        tableRepo.flush();
        List<TableViewDto> viewTop30 = tableService.view();

        // Then
        assertEquals(viewTop30.size(), 30);
        AtomicInteger j = new AtomicInteger(40);
        // TableService 의 view 로직이 올바르게 적용되면 j.get을 했을떄 값이 10이 나와야 한다. 40 - 30 = 10
        for(TableViewDto v : viewTop30 ) {
            assertEquals(v.getGoods(), j.getAndDecrement());
        }
        assertEquals(j.get(), 10);
    }

    @Test
    @DisplayName("TableService viewAll 검증")
    void TableService_viewAll_검증(){
        String content = "고양이는 귀엽지 않아?!!!";
        // Given
        List<TableDomain> tableDomains = Stream.generate(
                () -> TableDomain.builder()
                        .content(content)
                        .build()
        ).limit(45).collect(Collectors.toList());

        // When
        tableRepo.saveAll(tableDomains);
        tableRepo.flush();
        List<TableViewDto> tableViewAll = tableService.viewAll();

        // Then
        assertEquals(tableViewAll.size(), 45); // 45개가 모두다 보여지는지
        for(TableViewDto t : tableViewAll){
            assertEquals(t.getContent(), content);
        }
    }
}