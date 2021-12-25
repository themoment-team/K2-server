package com.moment.the.improvement.service;

import com.moment.the.admin.AdminDomain;
import com.moment.the.admin.repository.AdminRepository;
import com.moment.the.admin.service.AdminServiceImpl;
import com.moment.the.exception.ErrorCode;
import com.moment.the.exception.exceptionCollection.AccessNotFoundException;
import com.moment.the.exception.exceptionCollection.UserNotFoundException;
import com.moment.the.improvement.ImprovementDomain;
import com.moment.the.improvement.dto.ImprovementDto;
import com.moment.the.improvement.repository.ImprovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImprovementService {
    private final ImprovementRepository improvementRepository;
    private final AdminRepository adminRepository;

    // Create improvement.
    @Transactional
    public ImprovementDomain createThisImprovement(ImprovementDto.Request request){
        try {
            AdminDomain adminDomain = adminRepository.findByEmail(AdminServiceImpl.getUserEmail());
            return improvementRepository.save(request.toEntity(adminDomain));
        } catch (UserNotFoundException e){
            throw new UserNotFoundException("Can't find user by email", ErrorCode.USER_NOT_FOUND);
        }
    }

    /**
     * improvement view
     * TODO ImprovementViewAllDto class 사용
     *
     * @return List<ImprovementViewAllDto>
     * @author 전지환
     */
    public List<ImprovementDto.Response> getThisImprovement(){
        return improvementRepository.getAllImprovement();
    }

    // Update improvement.
    @Transactional
    public void updateThisImprovement(ImprovementDto.Request request, Long improveIdx){
        // 개선 사례 가져오기
        ImprovementDomain improvementDomain = improvementRepository.findByImproveIdx(improveIdx);
        if(improvementDomain.getAdminDomain().getEmail().equals(AdminServiceImpl.getUserEmail())){
            improvementDomain.update(request);
        } else {
            throw new AccessNotFoundException("NO Access to update this improvement", ErrorCode.ACCESS_NOT_FOUND);
        }
    }

    // Delete improvement.
    @Transactional
    public void deleteThisImprovement(Long improveIdx){
        ImprovementDomain selectImprove = improvementRepository.findByImproveIdx(improveIdx);
        if(selectImprove.getAdminDomain().getEmail().equals(AdminServiceImpl.getUserEmail())){
            improvementRepository.delete(selectImprove);
        } else {
            throw new AccessNotFoundException("No access to delete this improvement", ErrorCode.ACCESS_NOT_FOUND);
        }
    }
}
