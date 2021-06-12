package com.moment.the.service;

import com.moment.the.config.security.MyUserDetailsService;
import com.moment.the.domain.AdminDomain;
import com.moment.the.domain.AnswerDomain;
import com.moment.the.domain.TableDomain;
import com.moment.the.dto.AdminDto;
import com.moment.the.dto.AnswerDto;
import com.moment.the.dto.TableDto;
import com.moment.the.repository.AnswerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class AnswerServiceTest {

    @Autowired AdminService adminService;
    @Autowired AnswerService answerService;
    @Autowired AnswerRepository answerRepo;
    @Autowired TableService tableService;
    @Autowired MyUserDetailsService userDetailsService;

    final String ADMIN_ID = "email@email";
    final String PASSWORD = "password";
    final String ROLE = "ADMIN";


    @Test @DisplayName("답변 작성하기 (save) 검증")
//    @WithMockUser(username = ADMIN_ID, password = PASSWORD, authorities = ROLE)
    @WithAnonymousUser
    void save_검증() throws Exception {
        // Given
        AdminDto adminDto = new AdminDto(ADMIN_ID, PASSWORD, ADMIN_ID);
        adminService.signUp(adminDto);

        String tableContent = "급식이 맛이 없어요 급식에 질을 높여주세요!";
        TableDto tableDto = new TableDto(tableContent);
        TableDomain tableDomain = tableService.write(tableDto);

        String answerContent = "급식이 맛이 없는 이유는 삼식이라 어쩔수 없어요~";
        AnswerDto answerDto = new AnswerDto(answerContent, null);

        // When
        answerService.save(answerDto, tableDomain.getBoardIdx());
        AnswerDomain savedAnswer = answerRepo.findTop1ByTableDomain_BoardIdx(tableDomain.getBoardIdx());

        // Then
        assertEquals(savedAnswer.getTableDomain(), tableDomain);
    }

}