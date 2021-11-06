package com.moment.the.util.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class HttpRequestControllerTest {
    @Autowired
    private HttpRequestController httpRequestController;

    @Test
    @DisplayName("client ip를 가져오는 api가 잘 작동하나요?")
    void getClientIpControllerTest(){

    }
}