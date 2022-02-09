package com.moment.the.meta.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class MetaService {

    /**
     * Use spring PSA:: static method mockito로 테스트하기 까다롭다.
     *
     * @author 전지환
     */
    @Service
    interface LocalDateService {
        LocalDate getLocalDateNow();
        LocalDate getReleaseDate();
    }

    @Service
    static class MetaLocalDateService implements LocalDateService{
        @Override
        public LocalDate getLocalDateNow() {
            return LocalDate.now();
        }

        @Override
        public LocalDate getReleaseDate() {
            return LocalDate.of(2021, 6, 7);
        }
    }

    private final LocalDateService localDateService;

    // LocalDateService로 추상화 타입을 잡고 MetaLocalDateService를 주입한다.
    public MetaService(MetaLocalDateService metaLocalDateService) {
        this.localDateService = metaLocalDateService;
    }

    /**
     * 오늘을 기준으로 프로젝트 시행 기간을 가져온다.
     *
     * @return int - termProjectStart
     */
    public int getTermProjectStart(){
        return calculateAfterDate(localDateService.getLocalDateNow());
    }

    /**
     * 프로젝트 기간을 계산하는 메서드.
     *
     * @param today 오늘 날짜
     * @return int - D-day after
     */
    private int calculateAfterDate(LocalDate today) {
        //  releaseDate: every-moment 출시일
        final LocalDate releaseDate = localDateService.getReleaseDate();

        // every-moment 출시일 by 오늘
        return (int) releaseDate.until(today, ChronoUnit.DAYS);
    }
}
