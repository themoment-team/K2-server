package com.moment.the.service;

import com.moment.the.advice.exception.*;
import com.moment.the.domain.AdminDomain;
import com.moment.the.domain.AnswerDomain;
import com.moment.the.domain.TableDomain;
import com.moment.the.dto.AnswerDto;
import com.moment.the.repository.AdminRepository;
import com.moment.the.repository.AnswerRepository;
import com.moment.the.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class AnswerService {
    final private AdminRepository adminRepo;
    final private AnswerRepository answerRepo;
    final private TableRepository tableRepo;

    // 답변 작성하기
    public void save(AnswerDto answerDto, Long boardIdx) throws Exception {
        //예외 처리
        TableDomain tableDomain = tableFindBy(boardIdx); // table 번호로 찾고 없으면 Exception
        boolean existAnswer = tableDomain.getAnswerDomain() != null ? true : false;
        if(existAnswer)
            throw new AnswerAlreadyExistsException();

        // Current UserEmail 구하기
        String userEmail = getUserEmail();
        AdminDomain adminDomain = adminRepo.findByAdminId(userEmail);

        // AnswerDomain 생성 및 Table 과의 연관관계 맻음
        answerDto.setAdminDomain(adminDomain);
        AnswerDomain saveAnswerDomain = answerDto.toEntity();
        saveAnswerDomain.updateTableDomain(tableDomain);

        answerRepo.save(saveAnswerDomain);
    }

    // 답변 수정하기
    @Transactional
    public void update(AnswerDto answerDto, Long answerIdx) throws Exception {
        // Current UserEmail 구하기
        String UserEmail = getUserEmail();
        AdminDomain adminDomain = adminRepo.findByAdminId(UserEmail);
        if(adminDomain == null){
            throw new UserNotFoundException();
        }

        // 해당하는 answer 찾기
        AnswerDomain answerDomain = answerFindBy(answerIdx);

        // 답변 업데이트하기
        answerDomain.update(answerDto);
    }

    public Map<String, String> view(Long boardIdx) throws Exception {
        // 해당 boardIdx를 참조하는 answerDomain 찾기.
        AnswerDomain answerDomain = answerRepo.findTop1ByTableDomain_BoardIdx(boardIdx);
        if(answerDomain == null){
            throw new NoCommentException();
        }
        // Data 반환.
        Map<String, String> answerContentResponse = new HashMap<>();
        answerContentResponse.put("title", answerDomain.getTableDomain().getContent());
        answerContentResponse.put("answerContent", answerDomain.getAnswerContent());
        answerContentResponse.put("writer", answerDomain.getAdminDomain().getAdminName());

        return answerContentResponse;
    }

    // 답변 삭제하기
    @Transactional
    public void delete(Long answerIdx) throws Exception {
        // 해당하는 answer 찾기
        AnswerDomain answerDomain = answerFindBy(answerIdx);
        AdminDomain getAnswerAdmin = answerDomain.getAdminDomain();

        AdminDomain loginAdmin = adminRepo.findByAdminId(getUserEmail());
        boolean isAdminOwnerThisAnswer = getAnswerAdmin == loginAdmin;
        if(isAdminOwnerThisAnswer)
            throw new AccessNotFoundException();

        // answer 삭제하기
        deleteAnswer(answerDomain);
    }

    // answerIdx 로 해당 answer 찾기
    public AnswerDomain answerFindBy(Long answerId){
        return answerRepo.findById(answerId).orElseThrow(NoCommentException::new);
    }

    // AdminDomain 로 해당 answer 찾기
    public AnswerDomain answerFindBy(AdminDomain adminDomain){
        return answerRepo.findByAdminDomain(adminDomain).orElseThrow(() -> new IllegalArgumentException("해당 답변은 없습니다."));
    }

    // tableIdx 로 해당 table 찾기
    public TableDomain tableFindBy(Long tableId){
        return tableRepo.findById(tableId).orElseThrow(NoPostException::new);
    }

    // Current userEmail 을 가져오기.
    public String getUserEmail() {
        String userEmail;
        AdminDomain principal = (AdminDomain) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof AdminDomain) {
            userEmail = ((UserDetails)principal).getUsername();
        } else {
            userEmail = principal.toString();
        }
        return userEmail;
    }

    public void deleteAnswer(AnswerDomain answerDomain){
        Long answerIdx = answerDomain.getAnswerIdx();
        answerDomain.getTableDomain().updateAnswerDomain(null); // 외래키 제약조건으로 인한 오류 해결
        answerRepo.deleteAllByAnswerIdx(answerIdx);
    }
}
