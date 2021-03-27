package com.moment.the.service;

import com.moment.the.domain.ImprovementDomain;
import com.moment.the.dto.ImprovementDto;
import com.moment.the.repository.ImprovementRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ImprovementService {
    private final ImprovementRepository improvementRepository;
    public ImprovementService(ImprovementRepository improvementRepository) {
        this.improvementRepository = improvementRepository;
    }
    // Create improvement.
    @Transactional
    public ImprovementDomain create(ImprovementDto improvementDto){
        ImprovementDomain improvementDomain = ImprovementDomain.builder()
                .improveHeader(improvementDto.getImproveHeader())
                .improveContent(improvementDto.getImproveContent())
                .build();
        String UserEmail = GetUserEmail();
        System.out.println("ImprovementDomain = " + UserEmail);
        return improvementRepository.save(improvementDomain);
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
