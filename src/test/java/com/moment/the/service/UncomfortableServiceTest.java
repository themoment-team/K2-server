package com.moment.the.service;

import com.moment.the.exceptionAdvice.exception.GoodsNotCancelException;
import com.moment.the.uncomfortable.*;
import com.moment.the.uncomfortable.dto.UncomfortableResponseDto;
import com.moment.the.uncomfortable.dto.UncomfortableSetDto;
import com.moment.the.uncomfortable.repository.UncomfortableRepository;
import com.moment.the.uncomfortable.service.UncomfortableService;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;
import java.time.*;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class UncomfortableServiceTest {

    @Autowired
    UncomfortableRepository tableRepo;
    @Autowired
    UncomfortableService uncomfortableService;


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
        UncomfortableDomain writeTable = uncomfortableService.createThisUncomfortable(uncomfortableSetDto);
        UncomfortableDomain savedTable = tableRepo.findByUncomfortableIdx(writeTable.getUncomfortableIdx()).orElseThrow(() -> new IllegalArgumentException("Table을 찾을 수 없습니다. (테스트실패)"));
        tableRepo.delete(savedTable);

        // then
        assertEquals(uncomfortableSetDto.getContent(), savedTable.getContent());
    }

    @Test
    @DisplayName("TableService top30 보여주기(top30View) 검증")
    void TableService_top30View_검증(){
        // Given
        AtomicInteger i = new AtomicInteger(1);
        List<UncomfortableDomain> uncomfortableEntities = Stream.generate(
                () -> UncomfortableDomain.builder()
                        .goods(i.getAndIncrement())
                        .content("TableService top30 보여주기 테스트")
                        .build()
        ).limit(40).collect(Collectors.toList());

        // When
        tableRepo.saveAll(uncomfortableEntities);
        List<UncomfortableResponseDto> viewTop30 = uncomfortableService.getRank();

        // Then
        assertEquals(viewTop30.size(), 30);
        AtomicInteger j = new AtomicInteger(40);
        // TableService 의 top30View 로직이 올바르게 적용되면 j.get을 했을떄 값이 10이 나와야 한다.
        // 저장된Table - top30 = 40 - 30 = 10
        for(UncomfortableResponseDto v : viewTop30 ) {
            assertEquals(v.getGoods(), j.getAndDecrement());
        }
        assertEquals(j.get(), 10);
    }

    @Test
    @DisplayName("TableService viewAll 검증")
    void TableService_viewAll_검증(){
        // Given
        List<UncomfortableDomain> uncomfortableEntities = Stream.generate(
                () -> UncomfortableDomain.builder()
                        .content("TableService viewAll 검증")
                        .build()
        ).limit(10).collect(Collectors.toList());

        // When
        tableRepo.saveAll(uncomfortableEntities);
        List<UncomfortableResponseDto> tableViewAll = uncomfortableService.getAllUncomfortable();

        // Then
        assertEquals(tableViewAll.size(), 10); // 10개를 저장했으므로 tableViewAll 의 개수는 10개여야 한다.
        tableViewAll.stream().forEach( t -> System.out.println(t.getContent()));

    }

    @Test
    @DisplayName("TableService 전체 개시글 수 보여주기 (amountUncomfortableView)검증")
    void TableService_amountUncomfortableView_검증(){
        // Given
        List<UncomfortableDomain> uncomfortableEntities = Stream.generate(
                () -> UncomfortableDomain.builder()
                        .content("TableService amountUncomfortableView 검증")
                        .build()
        ).limit(10).collect(Collectors.toList());

        // When
        tableRepo.saveAll(uncomfortableEntities);
        Long amountUncomfortable = uncomfortableService.getNumberOfUncomfortable();

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
        assertEquals(uncomfortableService.getDateSinceProjectStart(), period.getDays()+1);;
    }

    @Test
    @DisplayName("TableService 좋아요 수 증가 로직 (goods) 검증")
    void TableService_goods_검증(){
        // Given
        UncomfortableDomain uncomfortableDomain = UncomfortableDomain.builder()
                .content("TableService_goods_검증")
                .build();

        // When
        UncomfortableDomain savedUncomfortableDomain = tableRepo.save(uncomfortableDomain);
        uncomfortableService.increaseLike(savedUncomfortableDomain.getUncomfortableIdx());
        UncomfortableDomain savedGoodsUncomfortableDomain = tableRepo.findByUncomfortableIdx(savedUncomfortableDomain.getUncomfortableIdx()).orElseThrow(() -> new IllegalArgumentException("좋아요를 받은 TableEntity를 찾을 수 없습니다."));

        // Then
        assertEquals(savedGoodsUncomfortableDomain.getGoods(), 1);
    }

    @Test
    @DisplayName("TableService 좋아요 수 감소 로직 (cancelGood) 검증")
    void TableService_cancelGood_검증(){
        // Given
        UncomfortableDomain uncomfortableDomain = UncomfortableDomain.builder()
                .content("TableService_goods_검증")
                .goods(1) // 좋아요 한개 지급
                .build();

        // When
        UncomfortableDomain savedUncomfortableDomain = tableRepo.save(uncomfortableDomain);
        uncomfortableService.decreaseLike(savedUncomfortableDomain.getUncomfortableIdx());
        UncomfortableDomain savedCancelGoodUncomfortableDomain = tableRepo.findByUncomfortableIdx(savedUncomfortableDomain.getUncomfortableIdx()).orElseThrow(() -> new IllegalArgumentException("좋아요를 취소한 TableEntity를 찾을 수 없습니다."));

        // Given
        assertEquals(savedCancelGoodUncomfortableDomain.getGoods(), 0);
    }

    @Test
    @DisplayName("TableService 좋아요 수 감소 로직 (cancelGood) 음수가 될경우 exception 검증")
    void TableService_cancelGood_exception_검증() throws Exception {
        // Given
        UncomfortableDomain uncomfortableDomain = UncomfortableDomain.builder()
                .content("TableService_goods_검증")
                .goods(0) // 좋아요 0개
                .build();

        // When
        UncomfortableDomain savedUncomfortableDomain = tableRepo.save(uncomfortableDomain);
        System.out.println(savedUncomfortableDomain.getUncomfortableIdx());

        assertThrows(GoodsNotCancelException.class, () ->{
            uncomfortableService.decreaseLike(savedUncomfortableDomain.getUncomfortableIdx());
        });

    }

    @Test @Disabled
    @DisplayName("cron식 기간에 알맞게 좋아요가 모두 0으로 초기화 되나요?")
    void formatAllGoodsIsWorking() throws InterruptedException {
        /**
         * uncomfortableEntities: 좋아요가 있는 불편함 2개
         * uncomfortableEntities_2: 좋아요가 없는 불편함 2개
         */
        List<UncomfortableDomain> uncomfortableEntities = Stream.generate(
                () -> UncomfortableDomain.builder()
                        .content("좋아요가 있는 불편함")
                        .goods(2)
                        .build()
        ).limit(2).collect(Collectors.toList());
        List<UncomfortableDomain> uncomfortableEntities_2 = Stream.generate(
                () -> UncomfortableDomain.builder()
                        .content("좋아요가 없는 불편함")
                        .goods(0)
                        .build()
        ).limit(2).collect(Collectors.toList());

        List<UncomfortableDomain> uncomfortableDomains = tableRepo.saveAll(uncomfortableEntities);
        List<UncomfortableDomain> uncomfortableDomains_2 = tableRepo.saveAll(uncomfortableEntities_2);

        Thread.sleep(12*1000);
    }

    @Test @Disabled
    @DisplayName("LocalDateTime과 TimeZone이 연관이 있나요?")
    void checkTimeSet(){
        // Given real-KST
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
        LocalDateTime now = LocalDateTime.now();
        log.info("============== this time is: {}", now);

        // Given - manipulation localDateTime
        ZoneId seoul = ZoneId.of("Asia/Seoul");
        ZonedDateTime theTime = ZonedDateTime.of(2021, 10, 13, 23, 59, 59, 0, seoul);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        LocalDateTime afterManipulationTime = LocalDateTime.now();
        log.info("============= modified date is: {}", afterManipulationTime);

        LocalDateTime of = LocalDateTime.of(2021, 10, 13, 23, 59, 59);
    }
}