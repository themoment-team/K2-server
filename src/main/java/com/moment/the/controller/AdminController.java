package com.moment.the.controller;

import com.moment.the.dto.AdminDto;
import com.moment.the.dto.SignInDto;
import com.moment.the.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/v1")
public class AdminController {
    @Autowired
    private AdminService adminService;
    // localhost:8080/v1/signup
    @PostMapping("/signup")
    public void signUp(@RequestBody AdminDto adminDto){
        adminService.signup(adminDto);
    }
    // localhost:8080/v1/signin
    @RequestMapping(value = "/login", method= RequestMethod.POST)
    public void signIn(@RequestBody SignInDto signInDto, HttpSession session){
        adminService.signin(signInDto);


        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(signInDto.getAdminId(), signInDto.getAdminPwd());
        Authentication authentication = AuthenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());

    }
}

