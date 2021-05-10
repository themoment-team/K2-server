package com.moment.the.service;

import com.moment.the.advice.exception.*;
import com.moment.the.domain.AdminDomain;
import com.moment.the.domain.AnswerDomain;
import com.moment.the.domain.TableDomain;
import com.moment.the.dto.AnswerDto;
import com.moment.the.dto.AnswerResDto;
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
    public void save(AnswerDto answerDto, Long boardIdx) {
        //예외 처리
        TableDomain tableDomain = tableFindBy(boardIdx); // table 번호로 찾고 없으면 Exception
        boolean existAnswer = tableDomain.getAnswerDomain() != null ? true : false;
        if(existAnswer) throw new AnswerAlreadyExistsException(); //이미 답변이 있으면 Exception

        AdminDomain adminDomain = adminRepo.findByAdminId(getLoginAdminEmail());

        // AnswerDomain 생성 및 Table 과의 연관관계 맻음
        answerDto.setAdminDomain(adminDomain);
        AnswerDomain saveAnswerDomain = answerDto.toEntity();
        saveAnswerDomain.updateTableDomain(tableDomain);

        answerRepo.save(saveAnswerDomain);
    }

    // 답변 수정하기
    @Transactional
    public void update(AnswerDto answerDto, Long answerIdx) {
        AnswerDomain answerDomain = answerFindBy(answerIdx); // 해당하는 answer 찾기
        AdminDomain answerAdmin = answerDomain.getAdminDomain();
        AdminDomain loginAdmin = adminRepo.findByAdminId(getLoginAdminEmail());

        answerOwnerCheck(answerAdmin, loginAdmin); // 자신이 작성한 답변인지 확인

        // 답변 업데이트하기
        answerDomain.update(answerDto);
    }

    public AnswerResDto view(Long boardIdx) {
        // 해당 boardIdx를 참조하는 answerDomain 찾기.
        AnswerDomain answerDomain = answerRepo.findTop1ByTableDomain_BoardIdx(boardIdx);

        AnswerResDto answerResDto = AnswerResDto.builder()
                .answerIdx(answerDomain.getAnswerIdx())
                .title(answerDomain.getTableDomain().getContent())
                .content(answerDomain.getAnswerContent())
                .writer(answerDomain.getAdminDomain().getAdminName())
                .build();

        return answerResDto;
    }

    // 답변 삭제하기
    @Transactional
    public void delete(Long answerIdx) {
        // 해당하는 answer 찾기
        AnswerDomain answerDomain = answerFindBy(answerIdx);
        AdminDomain answerAdmin = answerDomain.getAdminDomain();

        AdminDomain loginAdmin = adminRepo.findByAdminId(getLoginAdminEmail());
        answerOwnerCheck(answerAdmin, loginAdmin); // 자신이 작성한 답변인지 확인

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
    public String getLoginAdminEmail() {
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

    public void answerOwnerCheck(AdminDomain answerAdmin, AdminDomain loginAdmin){
        boolean isAdminOwnerThisAnswer = answerAdmin == loginAdmin;
        if(isAdminOwnerThisAnswer)
            throw new AccessNotFoundException();
    }
}
