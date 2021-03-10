package com.moment.the.service;

import com.moment.the.domain.AdminDomain;
import com.moment.the.domain.AuthDomain;
import com.moment.the.domain.AuthEnum;
import com.moment.the.dto.AdminDto;
import com.moment.the.dto.SignInDto;
import com.moment.the.repository.AdminRepository;
import com.moment.the.repository.AuthRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthRepository authRepository;
    // DI
    public AdminService(AdminRepository adminRepository, PasswordEncoder passwordEncoder, AuthRepository authRepository) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.authRepository = authRepository;
    }
    // 회원가입 기능.
    @Transactional
    public AdminDomain signup(AdminDto adminDto){
        AdminDomain current = adminRepository.findByAdminId(adminDto.getAdminId());
        if(current != null){
            throw new RuntimeException("이미 가입된 유저입니다.");
        }
        AdminDomain adminDomain = AdminDomain.builder()
                .adminName(adminDto.getAdminName())
                .adminId(adminDto.getAdminId())
                .adminPwd(passwordEncoder.encode(adminDto.getAdminPwd()))
                .authEnum(AuthEnum.ROLE_NotAccepted)
                .build();
        return adminRepository.save(adminDomain);
    }
    // 로그인 기능.
    @Transactional
    public void signin(SignInDto signInDto){
        AdminDomain adminDomain = adminRepository.findByAdminId(signInDto.getAdminId());
        if(adminDomain == null) {
            System.out.println("해당 회원 정보가 없습니다.");
        } else if(!passwordEncoder.matches(signInDto.getAdminPwd(), adminDomain.getAdminPwd())) {
            System.out.println("비밀번호를 확인해주세요.");
        }
        if(CheckAuth(signInDto.getAdminId())) {
            adminDomain.acceptRole();
            System.out.println("관리자 권한 등업 완료 되었습니다.");
        } else {
            adminDomain.waitRole();
        }
    }
    // 권한 부여 여부를 체크하는 함수.
    public boolean CheckAuth(String email) {
        AuthDomain authDomain = authRepository.findByAuthEmail(email);

        if(authDomain == null) {
            return false;
        }
        return true;
    }
}
