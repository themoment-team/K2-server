package com.moment.the.service;

import com.moment.the.exceptionAdvice.exception.AccessNotFoundException;
import com.moment.the.exceptionAdvice.exception.AnswerAlreadyExistsException;
import com.moment.the.answer.*;
import com.moment.the.answer.dto.AnswerDto;
import com.moment.the.answer.dto.AnswerResDto;
import com.moment.the.answer.repository.AnswerRepository;
import com.moment.the.answer.service.AnswerService;
import com.moment.the.config.security.auth.MyUserDetailsService;
import com.moment.the.admin.AdminDomain;
import com.moment.the.uncomfortable.UncomfortableEntity;
import com.moment.the.admin.dto.AdminDto;
import com.moment.the.uncomfortable.dto.UncomfortableSetDto;
import com.moment.the.admin.repository.AdminRepository;
import com.moment.the.uncomfortable.service.UncomfortableService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class AnswerServiceTest {

    @Autowired AdminRepository adminRepo;
    @Autowired
    AnswerService answerService;
    @Autowired
    AnswerRepository answerRepo;
    @Autowired
    UncomfortableService uncomfortableService;
    @Autowired MyUserDetailsService userDetailsService;

    final String USER_ID = "adminID";
    final String USER_PASSWORD = "adminPW";
    final String USER_NAME = "admin";

    // test 편의를 위한 회원가입 매서드
    void adminSignUp(String adminId, String password, String adminName) throws Exception {
        AdminDto adminDto = new AdminDto(adminId, password, adminName);
        adminRepo.save(adminDto.toEntity());
    }

    //test 편의를 위한 로그인 매서드
    AdminDomain adminLogin(String adminId, String password) {
        AdminDomain adminDomain = adminRepo.findByAdminIdAndAdminPwd(adminId, password);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                adminDomain.getAdminId(),
                adminDomain.getAdminPwd(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);

        return adminDomain;
    }

    //test 편의를 위한 Table 생성 메서드
    UncomfortableEntity createTable(){
        String TABLE_CONTENT = "급식이 맛이 없어요 급식에 질을 높여주세요!";
        UncomfortableSetDto uncomfortableSetDto = new UncomfortableSetDto(TABLE_CONTENT);
        UncomfortableEntity uncomfortableEntity = uncomfortableService.addUncomfortable(uncomfortableSetDto);
        return uncomfortableEntity;
    }

    @Test @DisplayName("답변 작성하기 (save) 검증")
    void save_검증() throws Exception {
        // Given
        //회원가입
        adminSignUp(USER_ID, USER_PASSWORD, USER_NAME);

        //로그인
        AdminDomain adminDomain = adminLogin(USER_ID, USER_PASSWORD);

        //Table 등록
        UncomfortableEntity uncomfortableEntity = createTable();

        //answer 입력
        String ANSWER_CONTENT = "급식이 맛이 없는 이유는 삼식이라 어쩔수 없어요~";
        AnswerDto answerDto = new AnswerDto(ANSWER_CONTENT, null);

        // When
        AnswerDomain savedAnswer = answerService.save(answerDto, uncomfortableEntity.getBoardIdx());

        // Then
        assertEquals(savedAnswer.getAnswerContent(), ANSWER_CONTENT);
        assertEquals(savedAnswer.getUncomfortableEntity(), uncomfortableEntity);
        assertEquals(savedAnswer.getAdminDomain(), adminDomain);
    }

    @Test @DisplayName("답변 작성하기 (save) 답변이 이미 있을경우 AnswerAlreadyExistsException 검증")
    void save_AnswerAlreadyExistsException_검증() throws Exception {
        // Given
        //회원가입
        adminSignUp(USER_ID, USER_PASSWORD, USER_NAME);

        //로그인
        AdminDomain adminDomain = adminLogin(USER_ID, USER_PASSWORD);

        //Table 등록
        UncomfortableEntity uncomfortableEntity = createTable();

        //answer 추가
        String ANSWER_CONTENT = "급식이 맛이 없는 이유는 삼식이라 어쩔수 없어요~";
        AnswerDto answerDto = new AnswerDto(ANSWER_CONTENT, null);
        answerService.save(answerDto, uncomfortableEntity.getBoardIdx());

        // When
        String ONCE_MORE_ANSWER_CONTENT = "급식이 맛이 없는 이유는 삼식이라 어쩔수 없어요~";
        AnswerDto onceMoreAnswerDto = new AnswerDto(ONCE_MORE_ANSWER_CONTENT, null);
        AnswerAlreadyExistsException throwAtSaveMethod =
                assertThrows(AnswerAlreadyExistsException.class,
                        () -> answerService.save(onceMoreAnswerDto, uncomfortableEntity.getBoardIdx())
                );

        // then
        assertEquals(throwAtSaveMethod.getClass(), AnswerAlreadyExistsException.class);
    }

    @Test @DisplayName("답변 수정하기 (update) 검증")
    void update_검증() throws Exception {
        // Given
        //회원가입
        adminSignUp(USER_ID, USER_PASSWORD, USER_NAME);

        //로그인
        adminLogin(USER_ID, USER_PASSWORD);
        UncomfortableEntity uncomfortableEntity = createTable();

        // 답변 등록
        String ANSWER_CONTENT = "급식이 맛이 없는 이유는 삼식이라 어쩔수 없어요~";
        AnswerDto answerDto = new AnswerDto(ANSWER_CONTENT, null);
        AnswerDomain savedAnswer = answerService.save(answerDto, uncomfortableEntity.getBoardIdx());
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
        UncomfortableEntity uncomfortableEntity = createTable();

        // 답변 등록
        adminSignUp(USER_ID, USER_PASSWORD, USER_NAME); // 답변 등록을 위한 회원가입
        adminLogin(USER_ID, USER_PASSWORD); // 답변 등록을 위한 로그인

        String ANSWER_CONTENT = "급식이 맛이 없는 이유는 삼식이라 어쩔수 없어요~";
        AnswerDto answerDto = new AnswerDto(ANSWER_CONTENT, null);
        AnswerDomain savedAnswer = answerService.save(answerDto, uncomfortableEntity.getBoardIdx());
        System.out.println("savedAnswer.getAnswerContent() = " + savedAnswer.getAnswerContent());

        // When
        AnswerResDto answerResDto = answerService.view(uncomfortableEntity.getBoardIdx());

        //than
        assertEquals(answerResDto.getAnswerIdx(), savedAnswer.getAnswerIdx());
        assertEquals(answerResDto.getTitle(), savedAnswer.getUncomfortableEntity().getContent());
        assertEquals(answerResDto.getWriter(), savedAnswer.getAdminDomain().getAdminName());
        assertEquals(answerResDto.getContent(), savedAnswer.getAnswerContent());
    }

    @Test @DisplayName("답변 삭제 (delete) 검증")
    void delete_검증() throws Exception {
        // Given
        //회원가입
        String USER_ID = "adminID";
        String USER_PASSWORD = "adminPW";
        String USER_NAME = "admin";

        adminSignUp(USER_ID, USER_PASSWORD, USER_NAME);

        //로그인
        adminLogin(USER_ID, USER_PASSWORD);
        UncomfortableEntity uncomfortableEntity = createTable();

        // 답변 등록
        String ANSWER_CONTENT = "급식이 맛이 없는 이유는 삼식이라 어쩔수 없어요~";
        AnswerDto answerDto = new AnswerDto(ANSWER_CONTENT, null);
        AnswerDomain savedAnswer = answerService.save(answerDto, uncomfortableEntity.getBoardIdx());

        // When
        answerService.delete(savedAnswer.getAnswerIdx());
        IllegalArgumentException deleteSuccessException = assertThrows(IllegalArgumentException.class,
                () -> answerRepo.findById(savedAnswer.getAnswerIdx())
                        .orElseThrow(() -> new IllegalArgumentException("AnswerDomain을 찾을 수 없으므로 테스트 성공."))
        );

        // Then
        assertEquals(deleteSuccessException.getClass(), IllegalArgumentException.class);
    }


    @Test @DisplayName("답변 삭제 (delete) 다른사람의 답변을 삭제할 경우 AccessNotFoundException 검증")
    void 다른사람의_답변을_삭제할경우_AccessNotFoundException_검증() throws Exception {
        // Given
        //회원가입
        final String ADMIN_A_ID = "adminAID";
        final String ADMIN_A_PW = "adminAPW";
        final String ADMIN_A_NAME = "adminA";

        final String ADMIN_B_ID = "adminBID";
        final String ADMIN_B_PW = "adminBPW";
        final String ADMIN_B_NAME = "adminB";

        adminSignUp(ADMIN_A_ID, ADMIN_A_PW, ADMIN_A_NAME);
        adminSignUp(ADMIN_B_ID, ADMIN_B_PW, ADMIN_B_NAME);

        //로그인
        adminLogin(ADMIN_A_ID, ADMIN_A_PW);

        adminSignUp("adminB", "adminB_PW", "adminB");

        UncomfortableEntity uncomfortableEntity = createTable();

        // 답변 등록
        String ANSWER_CONTENT = "급식이 맛이 없는 이유는 삼식이라 어쩔수 없어요~";
        AnswerDto answerDto = new AnswerDto(ANSWER_CONTENT, null);
        AnswerDomain savedAnswer = answerService.save(answerDto, uncomfortableEntity.getBoardIdx());

        // When
        adminLogin(ADMIN_B_ID, ADMIN_B_PW);
        AccessNotFoundException deleteFailException = assertThrows(AccessNotFoundException.class
                , () -> answerService.delete(savedAnswer.getAnswerIdx()));

        // Than
        assertEquals(deleteFailException.getClass(), AccessNotFoundException.class);
    }
}