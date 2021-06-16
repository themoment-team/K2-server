package com.moment.the.service;

import com.moment.the.advice.exception.NoImprovementException;
import com.moment.the.advice.exception.UserNotFoundException;
import com.moment.the.domain.AdminDomain;
import com.moment.the.domain.ImprovementDomain;
import com.moment.the.dto.ImprovementDto;
import com.moment.the.dto.ImprovementViewAllDto;
import com.moment.the.repository.AdminRepository;
import com.moment.the.repository.ImprovementRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImprovementService {
    private final ImprovementRepository improvementRepository;
    private final AdminRepository adminRepository;

    // Create improvement.
    @Transactional
    public ImprovementDomain save(ImprovementDto improvementDto){
        // 현재 user 정보를 가져오기
        String UserEmail = GetUserEmail();
        AdminDomain adminDomain = adminRepository.findByAdminId(UserEmail);
        if(adminDomain == null){
            throw new UserNotFoundException();
        }
        return improvementRepository.save(improvementDto.ToEntity(adminDomain));
    }

    // Read improvement.
    public List<ImprovementViewAllDto> read(){
        ModelMapper modelMapper = new ModelMapper();
        return improvementRepository.findAllByOrderByImproveIdxDesc().stream()
                .map(m -> modelMapper.map(m, ImprovementViewAllDto.class))
                .collect(Collectors.toList());
    }

    // Update improvement.
    @Transactional
    public void update(ImprovementDto improvementDto, Long improveIdx){
        // 현재 user 정보를 가져오기
        String UserEmail = GetUserEmail();
        AdminDomain adminDomain = adminRepository.findByAdminId(UserEmail);
        if(adminDomain == null){
            throw new UserNotFoundException();
        }
        // 개선 사례 가져오기
        ImprovementDomain improvementDomain = improvementRepository.findByImproveIdx(improveIdx);
        if (improvementDomain == null){
            throw new NoImprovementException();
        }
        // 개선 사례 업데이트 하기
        improvementDomain.update(improvementDto);
    }

    // Delete improvement.
    @Transactional
    public void delete(Long improveIdx){
        ImprovementDomain improvementDomain = improvementRepository.findByImproveIdx(improveIdx);
        if (improvementDomain == null){
            throw new NoImprovementException();
        }
        improvementRepository.deleteAllByImproveIdx(improvementDomain.getImproveIdx());
    }

    // Current UserEmail을 가져오기.
    public String GetUserEmail() {
        String userEmail;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails) {
            userEmail = ((UserDetails) principal).getUsername();
        } else {
            userEmail = principal.toString();
        }
        return userEmail;
    }
}
