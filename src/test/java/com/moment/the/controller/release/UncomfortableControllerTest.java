package com.moment.the.controller.release;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moment.the.uncomfortable.UncomfortableDomain;
import com.moment.the.uncomfortable.controller.UncomfortableController;
import com.moment.the.response.ResponseService;
import com.moment.the.uncomfortable.dto.UncomfortableResponseDto;
import com.moment.the.uncomfortable.dto.UncomfortableSetDto;
import com.moment.the.uncomfortable.repository.UncomfortableRepository;
import com.moment.the.uncomfortable.service.UncomfortableService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@DisplayName("TableController 테스트")
@Transactional
@Slf4j
class UncomfortableControllerTest {

    MockMvc mockMvc;
    ResultActions resultActions;
    @Autowired
    UncomfortableController uncomfortableController;
    @Autowired ResponseService resService;
    @Autowired
    UncomfortableRepository tableRepo;
    @Autowired
    UncomfortableService uncomfortableService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(uncomfortableController)
                .addFilters(new CharacterEncodingFilter("UTF-8", true)) // utf-8 필터 추가
                .build();
    }

    @AfterEach
    void showRequestResponse() throws Exception {
        log.info("Request Response result");
        resultActions.andDo(print());
        resultActions = null;
    }

    String objectToJson(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }

    @Test @DisplayName("[POST]/v1/uncomfortable write 검증")
    void write_검증() throws Exception {
        // Given
        UncomfortableSetDto uncomfortableSetDto = new UncomfortableSetDto("학교가 밥이 너무 맛이 없어요");
        String tableDtoConvertJson = objectToJson(uncomfortableSetDto);

        // When
        resultActions = mockMvc.perform(
                post("/v1/uncomfortable")
                        .content(tableDtoConvertJson)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // Then
        String successMsg = objectToJson(resService.getSuccessResult());
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(successMsg))
        ;
    }

    @Test @DisplayName("[GET]/v1/uncomfortable viewAll 검증")
    void viewAll_검증() throws Exception {
        // Given
        // 랜덤한 문자열 생성
        final List<String> TABLE_CONTENTS = Stream.generate(
                () -> RandomStringUtils.randomAlphabetic(15))
                .limit(3)
                .collect(Collectors.toList());

        AtomicInteger i = new AtomicInteger(0);
        List<UncomfortableDomain> uncomfortableEntities = Stream.generate(
                () -> UncomfortableDomain.builder()
                        .goods(0)
                        .content(TABLE_CONTENTS.get(i.getAndIncrement()))
                        .build()
        ).limit(3).collect(Collectors.toList());
        tableRepo.saveAll(uncomfortableEntities);

        // When
        resultActions = mockMvc.perform(
                get("/v1/uncomfortable")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // Than
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString(TABLE_CONTENTS.get(0))))
                .andExpect(content().string(containsString(TABLE_CONTENTS.get(1))))
                .andExpect(content().string(containsString(TABLE_CONTENTS.get(2))))
                ;
    }

    @Test @DisplayName("[GET]/uncomfortable/top30 top30 검증")
    void top30_검증() throws Exception {
        //Given
        AtomicInteger i = new AtomicInteger(1);
        List<UncomfortableDomain> uncomfortableEntities = Stream.generate(
                () -> UncomfortableDomain.builder()
                        .goods(i.getAndIncrement())
                        .content(RandomStringUtils.randomAlphabetic(15))
                        .build()
        ).limit(40).collect(Collectors.toList());

        tableRepo.saveAll(uncomfortableEntities);
        List<UncomfortableResponseDto> uncomfortableResponseDtos = uncomfortableService.getRank();
        String top30Data = objectToJson(uncomfortableResponseDtos);

        //When
        resultActions = mockMvc.perform(
                get("/v1/uncomfortable/rank")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString(top30Data)))
                ;
    }
    @Test @DisplayName("[PUT]/v1/uncomfortable/{uncomfortable} goods 추가")
    void goods_검증() throws Exception {
        //Given
        UncomfortableDomain uncomfortableDomain = UncomfortableDomain.builder()
                .content("학교 급식이 맛이 없어요")
                .build();
        Long uncomfortableIdx = tableRepo.save(uncomfortableDomain).getUncomfortableIdx();

        //When
        resultActions = mockMvc.perform(
                patch("/v1/uncomfortable/"+uncomfortableIdx+"/like-increase")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //Than
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(objectToJson(resService.getSuccessResult())))
        ;
    }

    @Test @DisplayName("[PUT]/v1/uncomfortable/cancel/{uncomfortableIdx} goods 감소")
    void goodCancel_검증() throws Exception {
        //Given
        UncomfortableDomain uncomfortableDomain = UncomfortableDomain.builder()
                .content("학교 급식이 맛이 없어요")
                .goods(1)
                .build();
        Long uncomfortableIdx = tableRepo.save(uncomfortableDomain).getUncomfortableIdx();

        //When
        resultActions = mockMvc.perform(
                patch("/v1/uncomfortable/"+uncomfortableIdx+"/like-decrease")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //Than
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(objectToJson(resService.getSuccessResult())))
        ;
    }

    @Test @DisplayName("[GET]/v1/uncomfortable/amount ")
    void amountUncomfortable_검증() throws Exception {
        //Given
        List<UncomfortableDomain> uncomfortableEntities = Stream.generate(
                () -> UncomfortableDomain.builder()
                        .content(RandomStringUtils.randomAlphabetic(15))
                        .build()
        ).limit(8).collect(Collectors.toList());
        tableRepo.saveAll(uncomfortableEntities);

        //When
        resultActions = mockMvc.perform(
                get("/v1/uncomfortable/amount")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.data").value(8))
                ;
    }

    @Disabled
    @Test @DisplayName("[GET]/v1/uncomfortable/dateSinceProjectStart")
    void dateSinceProjectStart_검증() throws Exception {
        //Given
        LocalDate startTheMoment = LocalDate.of(2021,6,7);
        LocalDate currentDate = LocalDate.now();

        Period period = startTheMoment.until(currentDate);

        int dateSinceProjectStart = period.getDays()+1;

        //When
        resultActions = mockMvc.perform(
                get("/v1/uncomfortable/dateSinceProjectStart")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //Then
        resultActions
                .andExpect(jsonPath("$.data").value(dateSinceProjectStart));
    }
}