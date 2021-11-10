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
    final private AdminRepository adminRepository;
    final private AnswerRepository answerRepository;
    final private UncomfortableRepository uncomfortableRepository;

    /**
     * Uncomfortable에 대한 Answer를 생성합니다.
     * @param answerDto 생성할 Answer의 정보를 가지고 있는 DTO
     * @param uncomfortableIdx Answer를 작성할 Uncomfortable의 idx
     * @throws AnswerAlreadyExistsException 답변이 이미 존재할 떄
     * @throws NoPostException 해당 Uncomfortable이 존재하지 않을 때
     * @return AnswerDomain - 저장한 AnswerDomain
     * @author 전지환, 정시원
     */
    // 답변 작성하기
    public AnswerDomain createThisAnswer(AnswerDto answerDto, long uncomfortableIdx) throws NoCommentException, AnswerAlreadyExistsException{
        // uncomfortable 번호로 찾고 없으면 Exception
        UncomfortableDomain uncomfortableDomain =
                uncomfortableRepository.findById(uncomfortableIdx).orElseThrow(NoPostException::new);
        boolean isExistAnswer = uncomfortableDomain.getAnswerDomain() != null;
        if(isExistAnswer) throw new AnswerAlreadyExistsException(); //이미 답변이 있으면 Exception

        AdminDomain adminDomain = adminRepository.findByEmail(AdminServiceImpl.getUserEmail());

        // AnswerDomain 생성 및 UncomfortableDomain과 연관관계 맻음
        answerDto.setAdminDomain(adminDomain);
        AnswerDomain saveAnswerDomain = answerDto.toEntity();
        saveAnswerDomain.updateAnswerDomain(uncomfortableDomain);

        return answerRepository.save(saveAnswerDomain);
    }

    /**
     * 답변을 수정한다.
     * @param answerDto 수정할 content를 가지고 있는 DTO
     * @param answerIdx 수정할 답변의 Idx
     * @throws NoCommentException 해당 답변이 존재하지 않을 때
     * @throws AccessNotFoundException 답변의 작성자가 아닐 때
     * @return 수정된 AnswerDomain객체
     * @author 전지환, 정시원
     */
    @Transactional
    public AnswerDomain updateThisAnswer(AnswerDto answerDto, Long answerIdx) throws NoCommentException, AccessNotFoundException{
        AnswerDomain answerDomain = findAnswerById(answerIdx); // 해당하는 answer 찾기
        AdminDomain answerAdmin = answerDomain.getAdminDomain();
        AdminDomain loginAdmin = adminRepository.findByEmail(AdminServiceImpl.getUserEmail());

        answerOwnerCheck(answerAdmin, loginAdmin); // 자신이 작성한 답변인지 확인

        // 답변 업데이트하기
        answerDomain.update(answerDto);

        return answerDomain;
    }

    /**
     * 답변을 조회한다.
     * @param uncomfortableIdx 답변이 작성된 uncomfortableIdx
     * @return 수정된 AnswerDomain객체
     * @author 전지환, 정시원
     */
    public AnswerResDto getThisAnswer(Long uncomfortableIdx) {
        // 해당 uncomfortableIdx를 참조하는 answerDomain 찾기.
        AnswerDomain answerDomain = answerRepository.findByUncomfortableIdx(uncomfortableIdx);

        return AnswerResDto.builder()
                .answerIdx(answerDomain.getAnswerIdx())
                .title(answerDomain.getUncomfortableDomain().getContent())
                .content(answerDomain.getContent())
                .writer(answerDomain.getAdminDomain().getName())
                .build();
    }

    /**
     * 답변을 제거한다.
     * @param answerIdx 제거할 AnswerIdx
     * @author 전지환 정시원
     */
    @Transactional
    public void deleteThisAnswer(Long answerIdx) throws NoCommentException, AccessNotFoundException{
        // 해당하는 answer 찾기
        AnswerDomain answerDomain = findAnswerById(answerIdx);
        AdminDomain answerAdmin = answerDomain.getAdminDomain();

        AdminDomain loginAdmin = adminRepository.findByEmail(AdminServiceImpl.getUserEmail());
        answerOwnerCheck(answerAdmin, loginAdmin); // 자신이 작성한 답변인지 확인

        // answer 삭제하기
        deleteAnswer(answerDomain);
    }

    // answerIdx 로 해당 answer 찾기
    private AnswerDomain findAnswerById(Long answerId) throws NoCommentException{
        return answerRepository.findById(answerId).orElseThrow(NoCommentException::new);
    }

    private void deleteAnswer(AnswerDomain answerDomain){
        Long answerIdx = answerDomain.getAnswerIdx();
        answerDomain.getUncomfortableDomain().updateAnswerDomain(null); // 외래키 제약조건으로 인한 오류 해결
        answerRepository.deleteAllByAnswerIdx(answerIdx);
    }

    private void answerOwnerCheck(final AdminDomain answerAdmin, final AdminDomain loginAdmin) throws AccessNotFoundException{
        if(answerAdmin != loginAdmin) throw new AccessNotFoundException();
    }
}
