package com.moment.the.meta.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class MetaService {
    /**
     * 오늘을 기준으로 프로젝트 시행 기간을 가져온다.
     *
     * @return int - termProjectStart
     */
    public int getTermProjectStart(){
        return calculateAfterDate(LocalDate.now());
    }

    /**
     * 프로젝트 기간을 계산하는 메서드.
     *
     * @param today 오늘 날짜
     * @return int - D-day after
     */
    public static int calculateAfterDate(LocalDate today) {
        //  theMomentStart: the-moment 시작 날짜
        LocalDate theMomentStart = LocalDate.of(2021, 6, 7);

        // the_moment 프로젝트를 시작한 날짜 by 오늘의 날짜
        return (int) theMomentStart.until(today, ChronoUnit.DAYS);
    }
}
