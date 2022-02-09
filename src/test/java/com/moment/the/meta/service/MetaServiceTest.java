package com.moment.the.meta.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@Slf4j
class MetaServiceTest {

    @Autowired
    private MetaService metaService;

    @Autowired
    private MetaService.LocalDateService metaLocalDateService;

    @Test
    @DisplayName("MetaService_getTermProjectStart()는 calculateAfterDate()에서 계산된 값을 정상적으로 리턴한다.")
    void metaService_getTermProjectStart_test(){
        final LocalDate localDateNow = metaLocalDateService.getLocalDateNow();

        final Integer calculateAfterDateValue = ReflectionTestUtils.invokeMethod(metaService, "calculateAfterDate", localDateNow);
        final int termProjectStartValue = metaService.getTermProjectStart();

        log.info("====== private calculateAfterDate() value: {} ============= real business logic: {} ", calculateAfterDateValue, termProjectStartValue);
        assertEquals(calculateAfterDateValue, termProjectStartValue);
    }

}