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
}