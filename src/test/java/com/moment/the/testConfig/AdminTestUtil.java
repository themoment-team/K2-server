package com.moment.the.testConfig;

import com.moment.the.admin.AdminDomain;
import com.moment.the.admin.repository.AdminRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * 테스트에서 빈번히 발생하는 작업에 대해 Bean으로 등록하여 사용할 수 있습니다.
 *
 * @since 1.3.1
 * @author 전지환
 */
@Component
@Slf4j
public class AdminTestUtil {

    @Autowired
    private AdminRepository adminRepository;

    private AdminDomain testSignUp(){
        AdminDomain adminDomain = AdminDomain.builder()
                .email(RandomStringUtils.randomAlphabetic(5))
                .password(RandomStringUtils.randomAlphabetic(5))
                .name(RandomStringUtils.randomAlphabetic(5))
                .roles(Collections.singletonList("ROLE_ADMIN"))
                .build();

        return adminRepository.save(adminDomain);
    }

    private AdminDomain testSignIn(String adminId, String password) {
        AdminDomain adminDomain = adminRepository.findByEmailAndPassword(adminId, password);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                adminDomain.getEmail(),
                adminDomain.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);

        return adminDomain;
    }

    /**
     * 회원 가입/로그인이 필요한 테스트케이스에서 사용할 수 있습니다. <br>
     * 해당 메소드는 로그인까지 지원합니다.
     *
     * @return adminId - 로그인 된 adminId
     * @author 전지환
     */
    public Long signUpSignInTest() {
        AdminDomain signUpAdmin = testSignUp();
        log.info("========== join success! ==========");

        AdminDomain adminDomain = testSignIn(signUpAdmin.getEmail(), signUpAdmin.getPassword());
        log.info("========== login success! ==========");

        return adminDomain.getAdminIdx();
    }
}
