package com.moment.the.service;

import com.moment.the.admin.dto.AdminDto;
import com.moment.the.admin.dto.SignInDto;
import com.moment.the.admin.repository.AdminRepository;
import com.moment.the.admin.service.AdminService;
import com.moment.the.admin.service.AdminServiceImpl;
import com.moment.the.exceptionAdvice.exception.UserNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class AdminServiceImplTest {
    @AfterEach
    public void dataClean(){
        adminRepository.deleteAll();
    }
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private AdminService adminService;
    @Autowired
    private AdminServiceImpl adminServiceImpl;

    @Test
    void 회원가입(){
        //Given
        AdminDto adminDto = new AdminDto();
        String email = "s20062@gsm.hs.kr";
        String adminName = "jihwan";
        String pw = "1234";

        //when
        adminDto.setPassword(passwordEncoder.encode(pw));
        adminDto.setEmail(email);
        adminDto.setName(adminName);
        adminRepository.save(adminDto.toEntity());

        //then
        assertEquals(adminDto.getEmail(), email);
        assertEquals(passwordEncoder.matches(pw,adminDto.getPassword()), true);
        assertEquals(adminDto.getName(), "jihwan");
    }

    @Test
    void 이_사용자가_있나요(){
        //Given
        AdminDto adminDto = new AdminDto();
        String alreadyEmail = "asdf@asdf";
        String email = "asdf@asdf";
        adminDto.setEmail(alreadyEmail);

        //when
        adminRepository.save(adminDto.toEntity());

        //then
        assertEquals(adminRepository.findByEmail(email) == null , false);

    }

    @Test
    void 로그인_하겠습니다(){
        //Given
        AdminDto adminDto = new AdminDto();

        String id = "s20062@gsm";
        adminDto.setEmail(id);

        String pw = "1234";
        adminDto.setPassword(passwordEncoder.encode(pw));

        adminRepository.save(adminDto.toEntity());

        //when
        if(adminRepository.findByEmail(id) == null){
            throw new UserNotFoundException();
        } else {
            // then
            assertEquals(passwordEncoder.matches(pw, adminDto.getPassword()), true);
        }
    }

    @Test
    void GetUserEmail(){
        //Given
        AdminDto adminDto = new AdminDto();
        String userEmail = "s20062@gsm";
        String pw = "1234";
        adminDto.setEmail(userEmail);
        adminDto.setPassword(passwordEncoder.encode(pw));
        adminRepository.save(adminDto.toEntity());
        System.out.println("======== saved =========");

        // when login session 발급
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                adminDto.getEmail(),
                adminDto.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);
        System.out.println("=================================");
        System.out.println(context);

        //then
        String currentUserEmail = adminServiceImpl.getUserEmail();
        assertEquals(currentUserEmail, "s20062@gsm");
    }

    @Test
    void 서비스_회원가입() throws Exception {
        //Given
        AdminDto adminDto = new AdminDto();
        adminDto.setEmail("s20062@gsm");
        adminDto.setPassword("1234");
        adminDto.setName("jihwan");

        //when
        adminService.join(adminDto);

        //then
        assertEquals(adminRepository.findByEmail("s20062@gsm") != null, true);
    }

    @Test @Disabled
    void 서비스_로그인() throws Exception {
        //Given
        AdminDto adminDto = new AdminDto();
        adminDto.setEmail("s20062@gsmasdf");
        adminDto.setPassword(passwordEncoder.encode("1234"));
        adminDto.setName("jihwan");

        //when
        adminRepository.save(adminDto.toEntity());

        //then
        assertEquals(adminService.login("s20062@gsmasdf","1234") == null, false);
    }

    @Test
    void 회원탈퇴() throws Exception {
        // Given 회원가입
        AdminDto adminDto = new AdminDto();
        adminDto.setName("jihwan");
        adminDto.setEmail("s20062@gsm");
        adminDto.setPassword(passwordEncoder.encode("1234"));
        adminRepository.save(adminDto.toEntity());
        System.out.println("=========is saved=========");

        // Given SignInDto
        SignInDto signInDto = new SignInDto();
        signInDto.setEmail("s20062@gsm");
        signInDto.setPassword("1234");
        System.out.println("======== is set ========");

        // when login session 발급
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                adminDto.getEmail(),
                adminDto.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);
        System.out.println("=================================");
        System.out.println(context);
        // when 회원탈퇴를 실행 했을 때.
        adminService.withdrawal(signInDto);
    }

    @Test
    void 시원이가_안믿는_매치스(){
        //Given
        String pw = "1234";
        //when
        String encodePw = passwordEncoder.encode(pw);
        System.out.println("====================");
        System.out.println(encodePw);
        //then
        assertEquals(passwordEncoder.matches(pw, encodePw), true);
    }

    @Test
    void 서비스_토큰_발급(){
        //Given
        boolean exceptionCatched = false;

        AdminDto adminDto = new AdminDto();
        adminDto.setEmail("admin@admin");
        adminDto.setPassword(passwordEncoder.encode("1234"));
        adminRepository.save(adminDto.toEntity());

        //When
        try {
            adminServiceImpl.login("admin@admin", "134");
        } catch (UserNotFoundException e) {
            exceptionCatched = true;
        }

        //Then
        assertTrue(exceptionCatched);
    }

    @Test @Disabled
    void 로그아웃(){
        //Given
        AdminDto adminDto = new AdminDto();
        String userEmail = "s20062@gsm";
        String pw = "1234";
        adminDto.setEmail(userEmail);
        adminDto.setPassword(passwordEncoder.encode(pw));
        adminRepository.save(adminDto.toEntity());
        System.out.println("======== saved =========");

        // when login session 발급
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                adminDto.getEmail(),
                adminDto.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);
        System.out.println("=================================");
        System.out.println(context);

        // When logout
        adminServiceImpl.logout();
    }
}
