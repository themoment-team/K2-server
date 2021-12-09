package com.moment.the.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class HttpRequestUtilsTest {

    /**
     * ip를 잘 반환하는지 테스트합니다.
     * @author 전지환
     * @version 1.3.1
     */
    @Test
    @DisplayName("getClientIpAddressIfServletRequestExist가 ip를 잘 반환하나요?")
    void getIpMethodIsWorking(){
        String ip = HttpRequestUtils.getClientIpAddressIfServletRequestExist();
        assertNotNull(ip);
    }
}