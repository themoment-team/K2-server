package com.moment.the.service;

import com.moment.the.exceptionAdvice.exception.GoodsNotCancelException;
import com.moment.the.uncomfortable.*;
import com.moment.the.uncomfortable.dto.UncomfortableSetDto;
import com.moment.the.uncomfortable.dto.UncomfortableGetDto;
import com.moment.the.uncomfortable.repository.UncomfortableRepository;
import com.moment.the.uncomfortable.service.TableService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TableServiceTest {

    @Autowired
    UncomfortableRepository tableRepo;
    @Autowired
    TableService tableService;


    // 데이터 섞임 방지 한개의 테스트가 끝날떄마다 DB의 저장내용을 삭제한다.
    @AfterEach
    public void cleanUp(){
        tableRepo.deleteAll();
    }

    @Test
    @DisplayName("TableService write 로직 검증")
    void TableService_write_로직검증(){
        // given
        UncomfortableSetDto uncomfortableSetDto = UncomfortableSetDto.builder()
                .content("TableService write 로직 검증")
                .build();

        // when
        UncomfortableEntity writeTable = tableService.write(uncomfortableSetDto);
        UncomfortableEntity savedTable = tableRepo.findByBoardIdx(writeTable.getBoardIdx()).orElseThrow(() -> new IllegalArgumentException("Table을 찾을 수 없습니다. (테스트실패)"));
        tableRepo.delete(savedTable);

        // then
        assertEquals(uncomfortableSetDto.getContent(), savedTable.getContent());
    }

    @Test
    @DisplayName("TableService top30 보여주기(top30View) 검증")
    void TableService_top30View_검증(){
        // Given
        AtomicInteger i = new AtomicInteger(1);
        List<UncomfortableEntity> uncomfortableEntities = Stream.generate(
                () -> UncomfortableEntity.builder()
                        .goods(i.getAndIncrement())
                        .content("TableService top30 보여주기 테스트")
                        .build()
        ).limit(40).collect(Collectors.toList());

        // When
        tableRepo.saveAll(uncomfortableEntities);
        List<UncomfortableGetDto> viewTop30 = tableService.top30View();

        // Then
        assertEquals(viewTop30.size(), 30);
        AtomicInteger j = new AtomicInteger(40);
        // TableService 의 top30View 로직이 올바르게 적용되면 j.get을 했을떄 값이 10이 나와야 한다.
        // 저장된Table - top30 = 40 - 30 = 10
        for(UncomfortableGetDto v : viewTop30 ) {
            assertEquals(v.getGoods(), j.getAndDecrement());
        }
        assertEquals(j.get(), 10);
    }

    @Test
    @DisplayName("TableService viewAll 검증")
    void TableService_viewAll_검증(){
        // Given
        List<UncomfortableEntity> uncomfortableEntities = Stream.generate(
                () -> UncomfortableEntity.builder()
                        .content("TableService viewAll 검증")
                        .build()
        ).limit(10).collect(Collectors.toList());

        // When
        tableRepo.saveAll(uncomfortableEntities);
        List<UncomfortableGetDto> tableViewAll = tableService.viewAll();

        // Then
        assertEquals(tableViewAll.size(), 10); // 10개를 저장했으므로 tableViewAll 의 개수는 10개여야 한다.
        tableViewAll.stream().forEach( t -> System.out.println(t.getContent()));

    }

    @Test
    @DisplayName("TableService 전체 개시글 수 보여주기 (amountUncomfortableView)검증")
    void TableService_amountUncomfortableView_검증(){
        // Given
        List<UncomfortableEntity> uncomfortableEntities = Stream.generate(
                () -> UncomfortableEntity.builder()
                        .content("TableService amountUncomfortableView 검증")
                        .build()
        ).limit(10).collect(Collectors.toList());

        // When
        tableRepo.saveAll(uncomfortableEntities);
        Long amountUncomfortable = tableService.amountUncomfortableView();

        // then
        assertEquals(amountUncomfortable, 10);
    }

    @Disabled
    @Test @DisplayName("TableService 프로젝트 시작 이후 날짜 보여주기 (dateSinceProjectStart) 검증")
    void TableService_dateSinceProjectStart_검증(){
        // Given
        LocalDate startTheMoment = LocalDate.of(2021,6,7);
        LocalDate currentDate = LocalDate.now();

        // When
        Period period = startTheMoment.until(currentDate);

        // Then
        assertEquals(tableService.dateSinceProjectStart(), period.getDays()+1);;
    }

    @Test
    @DisplayName("TableService 좋아요 수 증가 로직 (goods) 검증")
    void TableService_goods_검증(){
        // Given
        UncomfortableEntity uncomfortableEntity = UncomfortableEntity.builder()
                .content("TableService_goods_검증")
                .build();

        // When
        UncomfortableEntity savedUncomfortableEntity = tableRepo.save(uncomfortableEntity);
        tableService.goods(savedUncomfortableEntity.getBoardIdx());
        UncomfortableEntity savedGoodsUncomfortableEntity = tableRepo.findByBoardIdx(savedUncomfortableEntity.getBoardIdx()).orElseThrow(() -> new IllegalArgumentException("좋아요를 받은 TableEntity를 찾을 수 없습니다."));

        // Then
        assertEquals(savedGoodsUncomfortableEntity.getGoods(), 1);
    }

    @Test
    @DisplayName("TableService 좋아요 수 감소 로직 (cancelGood) 검증")
    void TableService_cancelGood_검증(){
        // Given
        UncomfortableEntity uncomfortableEntity = UncomfortableEntity.builder()
                .content("TableService_goods_검증")
                .goods(1) // 좋아요 한개 지급
                .build();

        // When
        UncomfortableEntity savedUncomfortableEntity = tableRepo.save(uncomfortableEntity);
        tableService.cancelGood(savedUncomfortableEntity.getBoardIdx());
        UncomfortableEntity savedCancelGoodUncomfortableEntity = tableRepo.findByBoardIdx(savedUncomfortableEntity.getBoardIdx()).orElseThrow(() -> new IllegalArgumentException("좋아요를 취소한 TableEntity를 찾을 수 없습니다."));

        // Given
        assertEquals(savedCancelGoodUncomfortableEntity.getGoods(), 0);
    }

    @Test
    @DisplayName("TableService 좋아요 수 감소 로직 (cancelGood) 음수가 될경우 exception 검증")
    void TableService_cancelGood_exception_검증() throws Exception {
        // Given
        UncomfortableEntity uncomfortableEntity = UncomfortableEntity.builder()
                .content("TableService_goods_검증")
                .goods(0) // 좋아요 0개
                .build();

        // When
        UncomfortableEntity savedUncomfortableEntity = tableRepo.save(uncomfortableEntity);
        System.out.println(savedUncomfortableEntity.getBoardIdx());

        assertThrows(GoodsNotCancelException.class, () ->{
            tableService.cancelGood(savedUncomfortableEntity.getBoardIdx());
        });

    }
}