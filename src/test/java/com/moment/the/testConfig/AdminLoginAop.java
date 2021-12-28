package com.moment.the.testConfig;

import com.moment.the.admin.AdminDomain;
import com.moment.the.admin.repository.AdminRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;


@Aspect
@Component
@Slf4j
public class AdminLoginAop {

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


    @Before(value = "execution(* com.moment.the.util.*(..))")
    public void signUpSignInTest(ProceedingJoinPoint pjp) throws Throwable {
        log.info("========== job start {}=========", LocalDateTime.now());

        AdminDomain signUpAdmin = testSignUp();
        AdminDomain adminDomain = testSignIn(signUpAdmin.getEmail(), signUpAdmin.getPassword());

        Object proceed = pjp.proceed();

        log.info("========== job finish {}=========", LocalDateTime.now());
    }
}
