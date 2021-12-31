package com.moment.the.improvement.service;

import com.moment.the.admin.AdminDomain;
import com.moment.the.admin.repository.AdminRepository;
import com.moment.the.admin.service.AdminServiceImpl;
import com.moment.the.exception.ErrorCode;
import com.moment.the.exception.exceptionCollection.AccessNotFoundException;
import com.moment.the.improvement.ImprovementDomain;
import com.moment.the.improvement.dto.ImprovementDto;
import com.moment.the.improvement.repository.ImprovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 실제개선사례 service
 *
 * @version 1.3.1
 * @author 전지환, 정시원
 */
@Service
@RequiredArgsConstructor
public class ImprovementService {
    private final ImprovementRepository improvementRepository;
    private final AdminRepository adminRepository;

    /**
     * 실제개선사례 작성
     *
     * @param request 실제개선사례 내용
     * @return ImproveIdx 등록된 실제개선사례 번호
     * @author 전지환, 정시원
     */
    @Transactional
    public Long createThisImprovement(ImprovementDto.Request request){
        AdminDomain adminDomain = adminRepository.findByEmail(AdminServiceImpl.getUserEmail());

        ImprovementDomain improvement = improvementRepository.save(request.toEntity(adminDomain));
        return improvement.getImproveIdx();
    }

    /**
     * 실제개선사례 조회
     *
     * @return List<ImprovementDto.Response>
     * @author 전지환, 정시원
     */
    public List<ImprovementDto.Response> getAllImprovement(){
        return improvementRepository.getAllImprovement();
    }

    /**
     * 실제개선사례 단건 조회
     *
     * @param improveIdx 조회 하고자 하는 개선사례 id
     * @return ImprovementDto.Response 조회한 개선사례 내용
     * @author 전지환
     */
    public ImprovementDto.Response findImprovementById(Long improveIdx){
        return improvementRepository.findImprovementById(improveIdx);
    }

    /**
     * 실제개선사례 수정
     *
     * @param request 변경할 실제개선사례 내용
     * @author 전지환, 정시원
     */
    @Transactional
    public void updateThisImprovement(ImprovementDto.Request request, Long improveIdx){
        // 수정 하고자 하는 실제개선사례 찾기.
        ImprovementDomain improvementDomain = improvementRepository.findByImproveIdx(improveIdx);

        if (!improvementDomain.getAdminDomain().getEmail().equals(AdminServiceImpl.getUserEmail())){
            throw new AccessNotFoundException("NO Access to update this improvement", ErrorCode.ACCESS_NOT_FOUND);
        }
        improvementDomain.update(request);
    }


    /**
     * 실제개선사례 삭제
     *
     * @param improveIdx 삭제 하고자 하는 실제개선사례
     * @author 전지환
     */
    @Transactional
    public void deleteThisImprovement(Long improveIdx){
        // 삭제 하고자 하는 실제개선사례 찾기.
        ImprovementDomain improvementDomain = improvementRepository.findByImproveIdx(improveIdx);

        if (!improvementDomain.getAdminDomain().getEmail().equals(AdminServiceImpl.getUserEmail())){
            throw new AccessNotFoundException("NO Access to update this improvement", ErrorCode.ACCESS_NOT_FOUND);
        }
        improvementRepository.delete(improvementDomain);
    }
}
