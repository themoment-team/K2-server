package com.moment.the.uncomfortable.service;

import com.moment.the.exception.ErrorCode;
import com.moment.the.exception.exceptionCollection.NoPostException;
import com.moment.the.uncomfortable.UncomfortableDomain;
import com.moment.the.uncomfortable.dto.UncomfortableResponseDto;
import com.moment.the.uncomfortable.dto.UncomfortableSetDto;
import com.moment.the.uncomfortable.repository.UncomfortableRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UncomfortableService {
    private final UncomfortableRepository uncomfortableRepository;

    /**
     * 학교의 불편함을 작성합니다.
     * @param uncomfortableSetDto
     * @return UncomfortableEntity
     */
    @Transactional
    public UncomfortableDomain createThisUncomfortable(UncomfortableSetDto uncomfortableSetDto){
        return uncomfortableRepository.save(uncomfortableSetDto.toEntity());
    }

    /**
     * 많은 학생들이 공감한 글 상위 30개를 선별하여 가져옵니다.
     * @return List&#60;UncomfortableGetDto&#62;
     * @author 정시원
     */
    public List<UncomfortableResponseDto> getRank() {
        return uncomfortableRepository.uncomfortableViewTopBy(30);
    }

    /**
     * 학교의 불편함 전체를 가져옵니다.
     * @return List&#60;UncomfortableGetDto&#62;
     * @author 정시원
     */
    public List<UncomfortableResponseDto> getAllUncomfortable(){
        return uncomfortableRepository.uncomfortableViewAll();
    }

    /**
     * 해당 불편함의 좋아요를 증가시킵니다.
     * @param uncomfortableIdx
     */
    @Transactional
    public void increaseLike(Long uncomfortableIdx){
        UncomfortableDomain uncomfortableDomain =
                uncomfortableRepository.findByUncomfortableIdx(uncomfortableIdx).orElseThrow(
                        () -> new NoPostException("Don't exist post", ErrorCode.NO_POST)
                );
        uncomfortableDomain.updateGoods(uncomfortableDomain.getGoods() + 1);
    }

    /**
     * 해당 불편함의 좋아요를 감소시킵니다.
     *
     * @param uncomfortableIdx
     * @author 정시원, 전지횐
     */
    @Transactional
    public void decreaseLike(Long uncomfortableIdx) {
        UncomfortableDomain uncomfortableDomain =
                uncomfortableRepository.findByUncomfortableIdx(uncomfortableIdx).orElseThrow(
                        ()-> new NoPostException("Don't exist post", ErrorCode.NO_POST)
                );
        int currentGoods = uncomfortableDomain.getGoods();
        if (currentGoods == 0)
            throw new IllegalStateException("Can't decrease like because it can't be negative number");

        uncomfortableDomain.updateGoods(currentGoods - 1);
    }

    /**
     * 해당 불편함을 삭제합니다.
     * @param uncomfortableIdx
     */
    @Transactional
    public void deleteUncomfortableByIdx(long uncomfortableIdx){
        uncomfortableRepository.deleteById(uncomfortableIdx);
    }

    /**
     * 불편함의 개수를 세어 가져옵니다.
     * @return Long
     * @author 정시원, 전지환
     */
    public Long getCountOfUncomfortable(){
        return uncomfortableRepository.count();
    }



    /**
     * 모든 게시글의 좋아요를 0으로 초기화 하는 스케쥴러
     * cron -> 요일(x)-매달-1,14일-00:00:00
     * @author 전지환
     */
    @Transactional
    @Scheduled(cron = "0 0 0 1,14 * ?")
    public void formatAllGoods(){
        log.info("======= Initialization scheduler operation: {}", LocalDateTime.now());
        long changesCount = uncomfortableRepository.formatAllGoods();
        log.info("======= {} changes have occurred at {}", changesCount, LocalDateTime.now());
    }
}
