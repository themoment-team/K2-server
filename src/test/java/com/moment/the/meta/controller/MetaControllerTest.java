package com.moment.the.meta.controller;

import com.moment.the.meta.service.MetaService;
import com.moment.the.testConfig.AbstractControllerTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@Slf4j
public class MetaControllerTest extends AbstractControllerTest {

    @Autowired
    private MetaController metaController;

    @Override
    protected Object controller() {
        return metaController;
    }

    @MockBean
    private MetaService mockMetaService;

    @Test
    @DisplayName("project term을 조회하면 비즈니스 로직에서 도출된 데이터를 담아 리턴한다.")
    void mocking_controller() throws Exception {
        // Given :: mockMetaService.getTermProjectStart()을 호출하면 244를 리턴한다.
        Mockito.when(mockMetaService.getTermProjectStart()).thenReturn(244);

        // When :: mockMetaService.getTermProjectStart() 호출 한다.
        final int termProjectStart = mockMetaService.getTermProjectStart();
        log.info("========= termProjectStart: {}", termProjectStart);

        // Then :: 244가 리턴된다.
        assertEquals(termProjectStart, 244);

        // Then :: responseBody에 {'data':244}가 리턴된다.
        this.mvc.perform(get("/meta/v1.3.1/term"))
                .andExpect(status().isOk())
                .andExpect(content().json("{'data':244}"));

    }

}
