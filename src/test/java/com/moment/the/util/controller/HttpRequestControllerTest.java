package com.moment.the.util.controller;

import com.moment.the.testConfig.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@Transactional
class HttpRequestControllerTest extends AbstractControllerTest {
    @Autowired
    private HttpRequestController httpRequestController;

    @Override
    protected Object controller() {
        return httpRequestController;
    }

    @Test
    @DisplayName("client ip를 가져오는 api가 잘 작동하나요?")
    void getClientIpControllerTest() throws Exception {
        mvc.perform(
                get("/v1/request/getIp")
        );
    }
}