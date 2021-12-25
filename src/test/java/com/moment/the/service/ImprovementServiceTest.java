package com.moment.the.service;

import com.moment.the.admin.AdminDomain;
import com.moment.the.admin.service.AdminService;
import com.moment.the.exception.exceptionCollection.UserNotFoundException;
import com.moment.the.improvement.ImprovementDomain;
import com.moment.the.admin.dto.AdminDto;
import com.moment.the.improvement.dto.ImprovementDto;
import com.moment.the.admin.repository.AdminRepository;
import com.moment.the.improvement.repository.ImprovementRepository;
import com.moment.the.improvement.service.ImprovementService;
import net.bytebuddy.utility.RandomString;
import org.apache.commons.lang3.RandomStringUtils;
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

import static org.junit.jupiter.api.Assertions.*;

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
        //Given improvement
        ImprovementDto.Request requestDto = ImprovementDto.Request.builder()
                .title(header)
                .content(content)
                .build();

        //when
        adminSignUp("s20062", "1234", "jihwan");
        System.out.println("========= saved =========");
        adminLogin("s20062", "1234");

        //then
        improvementService.createThisImprovement(requestDto);
    }

    @Test
    @Order(1)
    void 개선사례_작성() throws Exception {
        //Given improvement
        ImprovementDto.Request requestDto = ImprovementDto.Request.builder()
                .title("abcd")
                .content("efg")
                .build();

        //when
        adminSignUp("s20062", "1234", "jihwan");
        adminLogin("s20062", "1234");
        improvementService.createThisImprovement(requestDto);

        //Then
        assertNotNull(improvementRepository.findByContent("efg"));
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

        // when
        improvementRepository.saveAll(improvementDomains);

        //then
        assertEquals(20, improvementService.getThisImprovement().size());
    }

    @Test
    @Order(3)
    void 개선사례_수정() throws Exception {
        //Given - 기존 개선사례
        saveImprovement("hello", "it's me");
        Long currentIdx = improvementRepository.findByContent("it's me").getImproveIdx();

        //Given - 수정된 개선사례 내용
        ImprovementDto.Request requestDto = ImprovementDto.Request.builder()
                .title("abcd")
                .content("efg")
                .build();

        //When - 수정
        improvementService.updateThisImprovement(requestDto, currentIdx);
        System.out.println("============= 업데이트 완료 ============");

        //Then
        assertNotNull(improvementRepository.findByContent("efg"));
    }

    @Test
    @Order(4)
    void 개선사례_삭제() throws Exception {
        //Given
        saveImprovement("hello", "world");
        Long improveIdx = improvementRepository.findByContent("world").getImproveIdx();

        //When - 실제개선사례 삭제
        improvementService.deleteThisImprovement(improveIdx);

        //Then - 기존의 실제개선사례의 내용은 없어야 한다.
        assertNull(improvementRepository.findByContent("world"));
    }
}
