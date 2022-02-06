package com.moment.the.meta.service;

import lombok.extern.slf4j.Slf4j;
import org.codehaus.plexus.util.cli.Arg;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@SpringBootTest
@Transactional
@Slf4j
class MetaServiceTest {

    @Autowired
    private MetaService metaService;

    @Test
    @DisplayName("mocking 된 날짜를 기준으로 프로젝트 기간을 계산하여 반환한다.")
    void calculate_based_mocking_date(){

        final LocalDate prefixDate = LocalDate.of(2021, 1, 25);
        try(final MockedStatic<LocalDate> localDateMockedStatic = Mockito.mockStatic(LocalDate.class)){
            localDateMockedStatic.when(LocalDate::now).thenReturn(prefixDate);

            final int termProjectStart = metaService.getTermProjectStart();
            log.info("====== termProjectStart: {}", termProjectStart);
        }

    }
}