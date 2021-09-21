package com.moment.the.service;

import com.moment.the.admin.AdminDomain;
import com.moment.the.admin.service.AdminService;
import com.moment.the.improvement.ImprovementDomain;
import com.moment.the.admin.dto.AdminDto;
import com.moment.the.improvement.dto.ImprovementDto;
import com.moment.the.admin.repository.AdminRepository;
import com.moment.the.improvement.repository.ImprovementRepository;
import com.moment.the.improvement.service.ImprovementService;
import org.junit.jupiter.api.*;
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ImprovementServiceTest {

    // 데이터 섞임 방지 한개의 테스트가 끝날떄마다 DB의 저장내용을 삭제한다.
    @AfterEach
    public void cleanUp(){
        improvementRepository.deleteAll();
    }

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

    // test 편의를 위한 save 로직
    void saveImprovement(String header , String content) throws Exception {
        ImprovementDto improvementDto = new ImprovementDto();
        improvementDto.setTitle(header);
        improvementDto.setContent(content);

        //when
        adminSignUp("s20062", "1234", "jihwan");
        System.out.println("========= saved =========");
        adminLogin("s20062", "1234");
        improvementService.createThisImprovement(improvementDto);
    }

    @Test
    @Order(1)
    void 개선사례_작성() throws Exception {
        //Given
        ImprovementDto improvementDto = new ImprovementDto();
        improvementDto.setTitle("hello world");
        improvementDto.setContent("this is content");

        //when
        adminSignUp("s20062", "1234", "jihwan");
        System.out.println("========= saved =========");
        adminLogin("s20062", "1234");
        improvementService.createThisImprovement(improvementDto);

        //Then
        assertEquals(improvementRepository.findByContent("this is content")==null, false);
    }

    @Test
    @Order(2)
    void 개선사레_조회(){
        //Given
        List<ImprovementDomain> improvementDomains = Stream.generate(
                () ->  ImprovementDomain.builder()
                        .title("hello header")
                        .content("hello content")
                .build()
        ).limit(20).collect(Collectors.toList());

        improvementRepository.saveAll(improvementDomains);

        //when
        improvementService.getThisImprovement();

        //then
        assertEquals(20, improvementService.getThisImprovement().size());
    }

    @Test
    @Order(3)
    void 개선사례_수정() throws Exception {
        //Given
        saveImprovement("hello", "it's me");
        System.out.println("======== save 완료 ==========");
        Long currentIdx = improvementRepository.findByContent("it's me").getImproveIdx();

        //Given
        ImprovementDto improvementDto = new ImprovementDto();
        improvementDto.setTitle("이걸로 바꿀게용");
        improvementDto.setContent("이걸로 한다고용");

        //When
        improvementService.updateThisImprovement(improvementDto, currentIdx);
        System.out.println("============= 업데이트 완료 ============");

        //Then
        assertEquals(false, improvementRepository.findByContent("이걸로 한다고용") == null);
    }

    @Test
    @Order(4)
    void 개선사례_삭제() throws Exception {
        //Given
        saveImprovement("hello", "world");
        System.out.println("========save 완료==========");
        Long delIdx = improvementRepository.findByContent("world").getImproveIdx();

        //When
        improvementService.deleteThisImprovement(delIdx);
        System.out.println("==========삭제 완료===========");

        //Then
        assertEquals(true, improvementRepository.findByContent("world") == null);
    }
}
