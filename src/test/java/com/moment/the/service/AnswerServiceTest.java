package com.moment.the.service;

import com.moment.the.config.security.MyUserDetailsService;
import com.moment.the.domain.AdminDomain;
import com.moment.the.domain.AnswerDomain;
import com.moment.the.domain.TableDomain;
import com.moment.the.dto.AdminDto;
import com.moment.the.dto.AnswerDto;
import com.moment.the.dto.AnswerResDto;
import com.moment.the.dto.TableDto;
import com.moment.the.repository.AnswerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class AnswerServiceTest {

    @Autowired AdminService adminService;
    @Autowired AnswerService answerService;
    @Autowired AnswerRepository answerRepo;
    @Autowired TableService tableService;
    @Autowired MyUserDetailsService userDetailsService;

    // 유저정보
    final String ADMIN_ID = "email@email";
    final String PASSWORD = "password";
    final String ROLE = "ROLE_USER";

    // test 편의를 위한 회원가입 매서드
    void adminSignUp() throws Exception {
        AdminDto adminDto = new AdminDto(ADMIN_ID, PASSWORD, "Admin");
        adminService.signUp(adminDto);
    }

    //test 편의를 위한 로그인 매서드
    AdminDomain adminLogin() throws Exception {
        AdminDomain adminDomain = adminService.loginUser(ADMIN_ID, PASSWORD);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                adminDomain.getAdminId(),
                adminDomain.getAdminPwd(),
                List.of(new SimpleGrantedAuthority(ROLE)));
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);

        return adminDomain;
    }

    //test 편의를 위한 Table 생성 메서드
    TableDomain createTable(){
        String TABLE_CONTENT = "급식이 맛이 없어요 급식에 질을 높여주세요!";
        TableDto tableDto = new TableDto(TABLE_CONTENT);
        TableDomain tableDomain = tableService.write(tableDto);
        return tableDomain;
    }

    @Test @DisplayName("답변 작성하기 (save) 검증")
    void save_검증() throws Exception {
        // Given
        //회원가입
        adminSignUp();

        //로그인
        AdminDomain adminDomain = adminLogin();

        //Table 등록
        TableDomain tableDomain = createTable();

        //answer 입력
        String ANSWER_CONTENT = "급식이 맛이 없는 이유는 삼식이라 어쩔수 없어요~";
        AnswerDto answerDto = new AnswerDto(ANSWER_CONTENT, null);

        // When
        answerService.save(answerDto, tableDomain.getBoardIdx());
        AnswerDomain savedAnswer = answerRepo.findTop1ByTableDomain_BoardIdx(tableDomain.getBoardIdx());

        // Then
        assertEquals(savedAnswer.getAnswerContent(), ANSWER_CONTENT);
        assertEquals(savedAnswer.getTableDomain(), tableDomain);
        assertEquals(savedAnswer.getAdminDomain(), adminDomain);
    }

    @Test @DisplayName("답변 수정하기 (update) 검증")
    void update_검증() throws Exception {
        // Given
        adminSignUp();
        AdminDomain adminDomain = adminLogin();
        TableDomain tableDomain = createTable();

        // 답변 등록
        String ANSWER_CONTENT = "급식이 맛이 없는 이유는 삼식이라 어쩔수 없어요~";
        AnswerDto answerDto = new AnswerDto(ANSWER_CONTENT, null);
        answerService.save(answerDto, tableDomain.getBoardIdx());
        AnswerDomain savedAnswer = answerRepo.findTop1ByTableDomain_BoardIdx(tableDomain.getBoardIdx());
        System.out.println("savedAnswer.getAnswerContent() = " + savedAnswer.getAnswerContent());

        // When
        String CHANGE_ANSWER_CONTENT = "그냥 드세요 요구하는게 있으면 잃는것도 있어야지!";
        AnswerDto changeAnswerDto = new AnswerDto(CHANGE_ANSWER_CONTENT, null);
        answerService.update(changeAnswerDto, savedAnswer.getAnswerIdx());
        System.out.println("savedAnswer.getAnswerContent() = " + savedAnswer.getAnswerContent());

        // Than
        assertEquals(savedAnswer.getAnswerContent(), CHANGE_ANSWER_CONTENT);
    }

    @Test @DisplayName("답변 보기 (view) 검증")
    void view_검증() throws Exception {
        // Given
        adminSignUp();
        AdminDomain adminDomain = adminLogin();
        TableDomain tableDomain = createTable();

        // 답변 등록
        String ANSWER_CONTENT = "급식이 맛이 없는 이유는 삼식이라 어쩔수 없어요~";
        AnswerDto answerDto = new AnswerDto(ANSWER_CONTENT, null);
        answerService.save(answerDto, tableDomain.getBoardIdx());
        AnswerDomain savedAnswer = answerRepo.findTop1ByTableDomain_BoardIdx(tableDomain.getBoardIdx());
        System.out.println("savedAnswer.getAnswerContent() = " + savedAnswer.getAnswerContent());

        // When
        AnswerResDto answerResDto = answerService.view(tableDomain.getBoardIdx());

        //than
        assertEquals(answerResDto.getAnswerIdx(), savedAnswer.getAnswerIdx());
        assertEquals(answerResDto.getTitle(), savedAnswer.getTableDomain().getContent());
        assertEquals(answerResDto.getWriter(), savedAnswer.getAdminDomain().getAdminName());
        assertEquals(answerResDto.getContent(), savedAnswer.getAnswerContent());
    }

    @Test @DisplayName("답변 보기 (delete) 검증")
    void delete_검증() throws Exception {
        // Given
        adminSignUp();
        AdminDomain adminDomain = adminLogin();
        TableDomain tableDomain = createTable();

        // 답변 등록
        String ANSWER_CONTENT = "급식이 맛이 없는 이유는 삼식이라 어쩔수 없어요~";
        AnswerDto answerDto = new AnswerDto(ANSWER_CONTENT, null);
        answerService.save(answerDto, tableDomain.getBoardIdx());
        AnswerDomain savedAnswer = answerRepo.findTop1ByTableDomain_BoardIdx(tableDomain.getBoardIdx());

        // When
        answerService.delete(savedAnswer.getAnswerIdx());

        // Then
        // answer를 찾을 수 없으므로 exception 발생 (test 성공)
        assertThrows(IllegalArgumentException.class,
                () -> answerRepo.findById(savedAnswer.getAnswerIdx()).orElseThrow(() -> new IllegalArgumentException("AdminDomain을 찾을 수 없습니다.")));
    }
}