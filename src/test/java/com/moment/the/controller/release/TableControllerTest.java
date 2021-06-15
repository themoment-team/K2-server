package com.moment.the.controller.release;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moment.the.domain.TableDomain;
import com.moment.the.domain.response.ResponseService;
import com.moment.the.dto.TableDto;
import com.moment.the.dto.TableViewDto;
import com.moment.the.repository.TableRepository;
import com.moment.the.service.TableService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@DisplayName("TableController 테스트")
@Slf4j
class TableControllerTest {

    MockMvc mockMvc;
    @Autowired TableController tableController;
    @Autowired ResponseService resService;
    @Autowired TableRepository tableRepo;
    @Autowired TableService tableService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(tableController)
                .addFilters(new CharacterEncodingFilter("UTF-8", true)) // utf-8 필터 추가
                .build();
    }

    String objectToJson(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }

    @Test @DisplayName("[POST]/v1/uncomfortable write 검증")
    void write_검증() throws Exception {
        // Given
        TableDto tableDto = new TableDto("학교가 밥이 너무 맛이 없어요");
        String tableDtoConvertJson = objectToJson(tableDto);

        // When
        ResultActions writeRequest = mockMvc.perform(
                post("/v1/uncomfortable")
                        .content(tableDtoConvertJson)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // Then
        String successMsg = objectToJson(resService.getSuccessResult());

        writeRequest
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
        List<TableDomain> tableDomains = Stream.generate(
                () -> TableDomain.builder()
                        .goods(0)
                        .content(TABLE_CONTENTS.get(i.getAndIncrement()))
                        .build()
        ).limit(3).collect(Collectors.toList());
        tableRepo.saveAll(tableDomains);

        // When
        ResultActions getResult = mockMvc.perform(
                get("/v1/uncomfortable")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // Than
        getResult
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString(TABLE_CONTENTS.get(0))))
                .andExpect(content().string(containsString(TABLE_CONTENTS.get(1))))
                .andExpect(content().string(containsString(TABLE_CONTENTS.get(2))))
                ;
        log.debug("Response body\n{}", getResult.andDo(print()));

    }

    @Test @DisplayName("[GET]/uncomfortable/top30 top30 검증")
    void top30() throws Exception {
        //Given
        AtomicInteger i = new AtomicInteger(1);
        List<TableDomain> tableDomains = Stream.generate(
                () -> TableDomain.builder()
                        .goods(i.getAndIncrement())
                        .content(RandomStringUtils.randomAlphabetic(15))
                        .build()
        ).limit(40).collect(Collectors.toList());

        tableRepo.saveAll(tableDomains);
        List<TableViewDto> tableViewDtos = tableService.top30View();
        String top30Data = objectToJson(tableViewDtos);

        //When
        ResultActions top30Response = mockMvc.perform(
                get("/v1/uncomfortable/top30")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //Then
        top30Response
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString(top30Data)))
                ;
    }
}