package com.moment.the.service;

import com.moment.the.domain.AdminDomain;
import com.moment.the.dto.AdminDto;
import com.moment.the.dto.ImprovementDto;
import com.moment.the.repository.AdminRepository;
import com.moment.the.repository.ImprovementRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ImprovementServiceTest {

    @Autowired
    private AdminService adminService;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private ImprovementService improvementService;
    @Autowired
    private ImprovementRepository improvementRepository;

    // test 편의를 위한 회원가입 매서드
    void adminSignUp(String adminId, String password, String adminName) throws Exception {
        AdminDto adminDto = new AdminDto(adminId, password, adminName);
        adminService.signUp(adminDto);
    }


    // test 편의를 위한 로그인 매서드
    AdminDomain adminLogin(String adminId, String password) throws Exception {
        AdminDomain adminDomain = adminRepository.findByAdminId(adminId);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                adminDomain.getAdminId(),
                adminDomain.getAdminPwd(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);

        return adminDomain;
    }

    @Test
    void 개선사례_작성() throws Exception {
        //Given
        ImprovementDto improvementDto = new ImprovementDto();
        improvementDto.setImproveHeader("hello world");
        improvementDto.setImproveContent("this is content");

        //when
        adminSignUp("s20062", "1234", "jihwan");
        System.out.println("========= saved =========");
        adminLogin("s20062", "1234");
        improvementService.create(improvementDto);

        //Then
        assertEquals(improvementRepository.findByImproveContent("this is content")==null, false);
    }
}
