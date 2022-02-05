package com.moment.the.meta.controller;

import com.moment.the.testConfig.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
public class CatchError extends AbstractControllerTest {

    @Autowired
    private MetaController metaController;

    @Override
    protected Object controller() {
        return metaController;
    }

    @Test
    @DisplayName("mocking controller")
    void mocking_controller() throws Exception {
        this.mvc.perform(get("/meta/v1.3.1/term")).andExpect(status().isOk());
    }
}
