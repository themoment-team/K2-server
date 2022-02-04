package com.moment.the.meta.controller;

import com.moment.the.config.security.auth.MyUserDetailsService;
import com.moment.the.meta.service.MetaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@WebMvcTest(MetaController.class)
@AutoConfigureMockMvc
class MetaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean(name = "metaService")
    private MetaService metaService;


    @Test
    @DisplayName("프로젝트 릴리즈 날짜 - 오늘 term 조회가 정상 작동한다.")
    void checking_the_operating_period(){
        LocalDate prefixToday = LocalDate.of(2022, 1, 25);

        try(MockedStatic<LocalDate> mockedDate = mockStatic(LocalDate.class)){
            mockedDate.when(LocalDate::now).thenReturn(prefixToday);

            LocalDate everyMomentStart = LocalDate.of(2021, 6, 7);
            final int result = (int) everyMomentStart.until(prefixToday, ChronoUnit.DAYS);

            assertEquals(result, metaService.getTermProjectStart());

            this.mockMvc.perform(get("/meta/v1.3.1/term")).andExpect(status().isOk());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    void wowItGood() throws Exception {
        this.mockMvc.perform(get("/meta/v1.3.1/term")).andExpect(status().isOk());
    }

}