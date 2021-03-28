package com.moment.the.service;

import com.moment.the.domain.AdminDomain;
import com.moment.the.domain.AnswerDomain;
import com.moment.the.domain.TableDomain;
import com.moment.the.dto.AnswerDto;
import com.moment.the.dto.AnswerUpdateDto;
import com.moment.the.repository.AdminRepository;
import com.moment.the.repository.AnswerRepository;
import com.moment.the.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AnswerService {
    final private AdminRepository adminRepo;
    final private AnswerRepository answerRepo;
    final private TableRepository tableRepo;

    // 답변 작성하기
    public void save(AnswerDto answerDto) throws Exception {
        // table 번호로 찾고 값 넣기
        TableDomain table = tableFindBy(answerDto.getBoardIdx());
        answerDto.setTableDomain(table);
        // Current UserEmail 구하기
        String UserEmail = GetUserEmail();
        AdminDomain adminDomain = adminRepo.findByAdminId(UserEmail);
        if(adminDomain == null){
            throw new Exception("관리자만 작성 가능합니다.");
        }
        // UserEmail과 함께 저장하기
        answerRepo.save(answerDto.toEntity(adminDomain));
    }

    // 답변 수정하기
    @Transactional
    public void update(AnswerUpdateDto answerUpdateDto){
        AnswerDomain answer = answerFindBy(answerUpdateDto.getAnswerIdx());
        answer.update(answerUpdateDto.getContents());
    }

    // 답변 삭제하기
    public void delete(Long answerIdx){
        answerRepo.deleteById(answerIdx);
    }
    public AnswerDomain answerFindBy(Long answerId){
        return answerRepo.findById(answerId).orElseThrow(() -> new IllegalArgumentException("해당 답변은 없습니다."));
    }

    //idx 로 table 찾는 매서드
    public TableDomain tableFindBy(Long tableId){
        return tableRepo.findById(tableId).orElseThrow(() -> new IllegalArgumentException("해당 Table 은 없습니다."));
    }

    // Current UserEmail을 가져오기.
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
