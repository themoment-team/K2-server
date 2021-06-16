package com.moment.the.service;

import com.moment.the.domain.AdminDomain;
import com.moment.the.domain.ImprovementDomain;
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
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
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

    // test 편의를 위한 save 로직
    void saveImprovement(String header , String content) throws Exception {
        ImprovementDto improvementDto = new ImprovementDto();
        improvementDto.setImproveHeader(header);
        improvementDto.setImproveContent(content);

        //when
        adminSignUp("s20062", "1234", "jihwan");
        System.out.println("========= saved =========");
        adminLogin("s20062", "1234");
        improvementService.save(improvementDto);
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
        improvementService.save(improvementDto);

        //Then
        assertEquals(improvementRepository.findByImproveContent("this is content")==null, false);
    }

    @Test
    void 개선사레_조회(){
        //Given
        List<ImprovementDomain> improvementDomains = Stream.generate(
                () ->  ImprovementDomain.builder()
                        .improveHeader("hello header")
                        .improveContent("hello content")
                .build()
        ).limit(20).collect(Collectors.toList());

        improvementRepository.saveAll(improvementDomains);

        //when
        improvementService.read();

        //then
        assertEquals(20, improvementService.read().size());
    }

    @Test
    void 개선사례_수정() throws Exception {
        //Given
        saveImprovement("hello", "it's me");
        System.out.println("======== save 완료 ==========");
        ImprovementDto improvementDto = new ImprovementDto();
        improvementDto.setImproveHeader("이걸로 바꿀게용");
        improvementDto.setImproveContent("이걸로 한다고용");

        //When
        improvementService.update(improvementDto, 1L);
        System.out.println("============= 업데이트 완료 ============");

        //Then
        assertEquals(false, improvementRepository.findByImproveContent("이걸로 한다고용") == null);
    }

    @Test
    void 개선사례_삭제() throws Exception {
        //Given
        saveImprovement("hello", "world");
        System.out.println("========save 완료==========");

        //When
        improvementService.delete(1L);

        //Then
        assertEquals(true, improvementRepository.findByImproveContent("world") == null);
    }
}
