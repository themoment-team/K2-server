package com.moment.the.service;

import com.moment.the.advice.exception.NoCommentException;
import com.moment.the.advice.exception.NoPostException;
import com.moment.the.advice.exception.UserNotFoundException;
import com.moment.the.domain.AdminDomain;
import com.moment.the.domain.AnswerDomain;
import com.moment.the.domain.TableDomain;
import com.moment.the.domain.response.ListResult;
import com.moment.the.dto.AnswerDto;
import com.moment.the.repository.AdminRepository;
import com.moment.the.repository.AnswerRepository;
import com.moment.the.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AnswerService {
    final private AdminRepository adminRepo;
    final private AnswerRepository answerRepo;
    final private TableRepository tableRepo;

    // 답변 작성하기
    public void save(AnswerDto answerDto, Long boardIdx) throws Exception {
        // Current UserEmail 구하기
        String UserEmail = GetUserEmail();
        AdminDomain adminDomain = adminRepo.findByAdminId(UserEmail);
        if(adminDomain == null){
            throw new UserNotFoundException();
        }

        // table 번호로 찾고 없으면 Exception
        TableDomain table = tableFindBy(boardIdx);

        // UserEmail과 함께 저장하기
        answerRepo.save(answerDto.toEntity(answerDto.getContent(),adminDomain, table));
    }

    // 답변 수정하기
    @Transactional
    public void update(AnswerDto answerDto, Long answerIdx) throws Exception {
        // Current UserEmail 구하기
        String UserEmail = GetUserEmail();
        AdminDomain adminDomain = adminRepo.findByAdminId(UserEmail);
        if(adminDomain == null){
            throw new UserNotFoundException();
        }

        // 해당하는 answer 찾기
        AnswerDomain answerDomain = answerFindBy(answerIdx);

        // 답변 업데이트하기
        answerDomain.update(answerDto);
    }

    public ListResult<AnswerDomain> view(Long boardIdx) throws Exception {
        List<AnswerDomain> answerDomainList = answerRepo.findAllByTableDomain_BoardIdx(boardIdx);
        System.out.println("--------------------------------------");
        System.out.println(answerDomainList);
        return null;
    }

    // 답변 삭제하기
    @Transactional
    public void delete(Long answerIdx) throws Exception {
        // 해당하는 answer 찾기
        AnswerDomain answerDomain = answerFindBy(answerIdx);
        // answer 삭제하기
        answerRepo.deleteAllByAnswerIdx(answerIdx);
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
    public String GetUserEmail() {
        String userEmail;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails) {
            userEmail = ((UserDetails)principal).getUsername();
        } else {
            userEmail = principal.toString();
        }
        return userEmail;
    }
}
