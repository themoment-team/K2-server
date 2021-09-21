package com.moment.the.improvement.service;

import com.moment.the.admin.AdminDomain;
import com.moment.the.admin.dto.AdminDto;
import com.moment.the.admin.repository.AdminRepository;
import com.moment.the.admin.service.AdminService;
import com.moment.the.improvement.dto.ImprovementDto;
import com.moment.the.improvement.repository.ImprovementRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ImprovementServiceTest {
    @Autowired
    private ImprovementRepository improvementRepository;
    @Autowired
    private ImprovementService improvementService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private AdminRepository adminRepository;
    @AfterEach
    void deleteData(){
        improvementRepository.deleteAll();
    }

    // test 편의를 위한 회원가입 매서드
    void adminSignUp(String adminId, String password, String adminName) throws Exception {
        AdminDto adminDto = new AdminDto(adminId, password, adminName);
        adminService.join(adminDto);
    }

    // test 편의를 위한 로그인 매서드
    AdminDomain adminLogin(String adminId, String password) throws Exception {
        AdminDomain adminDomain = adminRepository.findByEmail(adminId);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                adminDomain.getEmail(),
                adminDomain.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);

        return adminDomain;
    }

    @Test
    void 실제개선사례작성() throws Exception {
        //Given admin
        String email = "asdf@gsm";
        String pw = "1234";
        String name = "jihwan";
        adminSignUp(email, pw, name);
        adminLogin(email, pw);

        //Given improvement
        ImprovementDto improvementDto = new ImprovementDto();
        improvementDto.setContent("Hello world");
        improvementDto.setContent("it's jihwan");

        //when
        improvementService.createThisImprovement(improvementDto);

        //then
        assertEquals(false, improvementRepository.findByContent("it's jihwan") == null);
        assertEquals(true, improvementRepository.findByContent("it's jihwan").getAdminDomain().getEmail().equals("asdf@gsm"));
    }

}