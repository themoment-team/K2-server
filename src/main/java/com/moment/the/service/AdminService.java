package com.moment.the.service;

import com.moment.the.domain.AdminDomain;
import com.moment.the.dto.AdminDto;
import com.moment.the.repository.AdminRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminService(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public AdminDomain signup(AdminDto adminDto){
        if(adminRepository.findByAdminId(adminDto.getAdminId()).orElse(null) != null){
            throw new RuntimeException("이미 가입된 유저입니다.");
        }
        AdminDomain adminDomain = AdminDomain.builder()
                .adminName(adminDto.getAdminName())
                .adminId(adminDto.getAdminId())
                .adminPwd(passwordEncoder.encode(adminDto.getAdminPwd()))
                .build();
        return adminRepository.save(adminDomain);
    }
}
