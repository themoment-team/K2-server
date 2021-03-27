package com.moment.the.service;

import com.moment.the.domain.AdminDomain;
import com.moment.the.domain.ImprovementDomain;
import com.moment.the.dto.ImprovementDto;
import com.moment.the.repository.AdminRepository;
import com.moment.the.repository.ImprovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    public ImprovementDomain create(ImprovementDto improvementDto){
        String UserEmail = GetUserEmail();
        AdminDomain adminDomain = adminRepository.findByAdminId(UserEmail);
        return improvementRepository.save(improvementDto.ToEntity(adminDomain));
    }

    // Read improvement.
    public List<ImprovementDomain> read(){
        return improvementRepository.findAll();
    }

    // Update improvement.
    @Transactional
    public void update(ImprovementDto improvementDto, Long improveIdx) throws Exception {
        ImprovementDomain improvementDomain = improvementRepository.findByImproveIdx(improveIdx);
        if (improvementDomain == null){
            throw new Exception("no improve posting");
        }
        improvementDomain.update(improvementDto);
    }

    // Delete improvement.
    @Transactional
    public void delete(Long improveIdx)throws Exception{
        ImprovementDomain improvementDomain = improvementRepository.findByImproveIdx(improveIdx);
        if (improvementDomain == null){
            throw new Exception("no improve posting");
        }
        improvementRepository.deleteAllByImproveIdx(improvementDomain.getImproveIdx());
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
