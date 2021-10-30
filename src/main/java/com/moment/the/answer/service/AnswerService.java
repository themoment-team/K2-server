package com.moment.the.answer.service;

import com.moment.the.admin.AdminDomain;
import com.moment.the.admin.repository.AdminRepository;
import com.moment.the.admin.service.AdminServiceImpl;
import com.moment.the.answer.AnswerDomain;
import com.moment.the.answer.dto.AnswerDto;
import com.moment.the.answer.dto.AnswerResDto;
import com.moment.the.answer.repository.AnswerRepository;
import com.moment.the.exceptionAdvice.exception.*;
import com.moment.the.uncomfortable.UncomfortableDomain;
import com.moment.the.uncomfortable.repository.UncomfortableRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Answer에 대한 비즈니스 로직을 가지고 있는 클래스
 * @since 1.0.0
 * @version 1.3.1
 * @author 전지환, 정시원
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AnswerService {
    final private AdminRepository adminRepo;
    final private AnswerRepository answerRepo;
    final private UncomfortableRepository tableRepo;

    /**
     * Uncomfortable에 대한 Answer를 생성합니다.
     * @param answerDto 생성할 answer의 정보를 가지고 있는 DTO
     * @param uncomfortableIdx Answer를 작성할 Uncomfortable의 idx
     * @throws AnswerAlreadyExistsException 답변이 이미 존재할 떄
     * @throws NoPostException 해당 Uncomfortable이 존재하지 않을 때
     * @return AnswerDomain - 저장한 AnswerDomain
     * @author 전지환, 정시원
     */
    // 답변 작성하기
    public AnswerDomain createThisAnswer(AnswerDto answerDto, Long uncomfortableIdx) {
        //예외 처리
        UncomfortableDomain uncomfortableDomain = tableFindBy(uncomfortableIdx); // table 번호로 찾고 없으면 Exception
        boolean existAnswer = uncomfortableDomain.getAnswerDomain() != null;
        if(existAnswer) throw new AnswerAlreadyExistsException(); //이미 답변이 있으면 Exception

        AdminDomain adminDomain = adminRepo.findByEmail(AdminServiceImpl.getUserEmail());

        // AnswerDomain 생성 및 Table 과의 연관관계 맻음
        answerDto.setAdminDomain(adminDomain);
        AnswerDomain saveAnswerDomain = answerDto.toEntity();
        saveAnswerDomain.updateAnswerDomain(uncomfortableDomain);

        return answerRepo.save(saveAnswerDomain);
    }

    // 답변 수정하기
    @Transactional
    public AnswerDomain updateThisAnswer(AnswerDto answerDto, Long answerIdx) {
        AnswerDomain answerDomain = answerFindBy(answerIdx); // 해당하는 answer 찾기
        AdminDomain answerAdmin = answerDomain.getAdminDomain();
        AdminDomain loginAdmin = adminRepo.findByEmail(AdminServiceImpl.getUserEmail());

        answerOwnerCheck(answerAdmin, loginAdmin); // 자신이 작성한 답변인지 확인

        // 답변 업데이트하기
        answerDomain.update(answerDto);

        return answerDomain;
    }

    public AnswerResDto getThisAnswer(Long uncomfortableIdx) {
        // 해당 uncomfortableIdx를 참조하는 answerDomain 찾기.
        AnswerDomain answerDomain = answerRepo.findTop1ByUncomfortableDomain_uncomfortableIdx(uncomfortableIdx);

        return AnswerResDto.builder()
                .answerIdx(answerDomain.getAnswerIdx())
                .title(answerDomain.getUncomfortableDomain().getContent())
                .content(answerDomain.getContent())
                .writer(answerDomain.getAdminDomain().getName())
                .build();
    }

    // 답변 삭제하기
    @Transactional
    public void deleteThisAnswer(Long answerIdx) {
        // 해당하는 answer 찾기
        AnswerDomain answerDomain = answerFindBy(answerIdx);
        AdminDomain answerAdmin = answerDomain.getAdminDomain();

        AdminDomain loginAdmin = adminRepo.findByEmail(AdminServiceImpl.getUserEmail());
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
    public UncomfortableDomain tableFindBy(Long tableId){
        return tableRepo.findById(tableId).orElseThrow(NoPostException::new);
    }

    private void deleteAnswer(AnswerDomain answerDomain){
        Long answerIdx = answerDomain.getAnswerIdx();
        answerDomain.getUncomfortableDomain().updateAnswerDomain(null); // 외래키 제약조건으로 인한 오류 해결
        answerRepo.deleteAllByAnswerIdx(answerIdx);
    }

    private void answerOwnerCheck(AdminDomain answerAdmin, AdminDomain loginAdmin){
        boolean isAdminOwnerThisAnswer = answerAdmin == loginAdmin;
        if(!isAdminOwnerThisAnswer) throw new AccessNotFoundException();
    }
}
