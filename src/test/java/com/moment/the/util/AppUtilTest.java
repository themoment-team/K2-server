package com.moment.the.util;

import com.moment.the.admin.AdminDomain;
import com.moment.the.testConfig.AdminTestUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AppUtilTest {

    @Autowired
    private AppUtil appUtil;
    @Autowired
    private AdminTestUtil adminTestUtil;

    @Test
    @DisplayName("현재 AdminEntity 가져오기")
    void getCurrentAdminEntityTest(){
        Long loginAdminId = adminTestUtil.signUpSignInTest();
        AdminDomain currentAdminEntity = appUtil.getCurrentAdminEntity();

        assertEquals(loginAdminId, currentAdminEntity.getAdminIdx());
    }
}