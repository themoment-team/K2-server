package com.moment.the.controller.release;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moment.the.domain.response.ResponseService;
import com.moment.the.dto.TableDto;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@DisplayName("TableController 테스트")
class TableControllerTest {

    MockMvc mockMvc;
    ObjectMapper objMapper;
    @Autowired TableController tableController;
    @Autowired ResponseService resService;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(tableController)
                .addFilters(new CharacterEncodingFilter("UTF-8", true)) // utf-8 필터 추가
                .build();
        objMapper = new ObjectMapper();
        objMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Test @DisplayName("[POST]/v1/uncomfortable write 검증")
    void write_검증() throws Exception {
        // Given
        TableDto tableDto = new TableDto("학교가 밥이 너무 맛이 없어요");
        String tableDtoConvertJson = objMapper.writeValueAsString(tableDto);

        // When
        ResultActions writeRequest = mockMvc.perform(
                post("/v1/uncomfortable")
                        .content(tableDtoConvertJson)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // Then
        String successMsg = objMapper.writeValueAsString(resService.getSuccessResult());

        writeRequest
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(successMsg))
        ;
    }
}