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
        String UserEmail = getUserEmail();
        try {
            AdminDomain adminDomain = adminRepository.findByAdminId(UserEmail);
            return improvementRepository.save(improvementDto.ToEntity(adminDomain));
        } catch (UserNotFoundException e){
            throw new UserNotFoundException();
        }
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
        try {
            String UserEmail = getUserEmail();
            AdminDomain adminDomain = adminRepository.findByAdminId(UserEmail);
        } catch (UserNotFoundException e){
            throw new UserNotFoundException();
        }
        // 개선 사례 가져오기
        try {
            ImprovementDomain improvementDomain = improvementRepository.findByImproveIdx(improveIdx);
            improvementDomain.update(improvementDto);
        } catch (NoImprovementException e){
            throw new NoImprovementException();
        }
    }

    // Delete improvement.
    @Transactional
    public void delete(Long improveIdx){
        try {
            ImprovementDomain selectImprove = improvementRepository.findByImproveIdx(improveIdx);
            improvementRepository.delete(selectImprove);
        } catch (NoImprovementException e){
            throw new NoImprovementException();
        }
    }

    // Current UserEmail을 가져오기.
    public String getUserEmail() {
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
