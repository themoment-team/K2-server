package com.moment.the.controller;

import com.moment.the.dto.AdminDto;
import com.moment.the.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/signup")
    public void signUp(@RequestBody AdminDto adminDto){
        adminService.signup(adminDto);
    }
}
