package com.moment.the.meta.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class MetaService {
    /**
     * 프로젝트 D-day를 세어 가져옵니다.
     * @return int
     */
    public int getTermProjectStart(){
        return calculateAfterDate();
    }

    /**
     * D-day를 계산하는 메서드.
     * @return int - D-day after
     */
    public static int calculateAfterDate() {
        //  today: 오늘 날짜
        //  theMomentStart: the-moment 시작 날짜
        LocalDate today = LocalDate.now();
        LocalDate theMomentStart = LocalDate.of(2021, 6, 7);

        // the_moment 프로젝트를 시작한 날짜 by 오늘의 날짜
        return (int) theMomentStart.until(today, ChronoUnit.DAYS);
    }
}
