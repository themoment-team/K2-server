package com.moment.the.controller;

import com.moment.the.dto.AdminDto;
import com.moment.the.dto.SignInDto;
import com.moment.the.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/login")
    public void signIn(@RequestBody SignInDto signInDto){
        adminService.signin(signInDto);
    }
}

