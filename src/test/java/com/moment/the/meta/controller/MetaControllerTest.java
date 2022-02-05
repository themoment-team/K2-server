package com.moment.the.meta.controller;

import com.moment.the.meta.service.MetaService;
import com.moment.the.testConfig.AbstractControllerTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@Slf4j
public class MetaControllerTest extends AbstractControllerTest {

    @Autowired
    private MetaController metaController;

    @Autowired
    private MetaService metaService;

    @Override
    protected Object controller() {
        return metaController;
    }

    @Test
    @DisplayName("project term을 조회하면 mocking된 날짜를 기준으로 term이 조회된다.")
    void mocking_controller() throws Exception {

        LocalDate prefixLocalDate = LocalDate.of(2022, 1, 25);

        // mocking:: prefixLocalDate를 LocalDate.now 를 호출할 때 반환한다.
        try(final MockedStatic<LocalDate> localDateMockedStatic = Mockito.mockStatic(LocalDate.class)){
            localDateMockedStatic.when(LocalDate::now).thenReturn(prefixLocalDate);

            log.info("======= LocalDate.now(): {} ======== prefixLocalDate: {} ", LocalDate.now(), prefixLocalDate);
            assertEquals(LocalDate.now(), prefixLocalDate);

            final int metaServiceTermValue = metaService.getTermProjectStart();
            final int mockingTermValue = MetaService.calculateAfterDate();

            log.info("======== metaService logic result: {} ========= mockTest logic result: {}", metaServiceTermValue, mockingTermValue);
            assertEquals(metaServiceTermValue, mockingTermValue);

        } catch (Exception e){
            System.out.println("e = " + e);
        }

        this.mvc.perform(get("/meta/v1.3.1/term")).andExpect(status().isOk());
    }
}
