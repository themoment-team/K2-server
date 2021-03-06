package com.moment.the.controller;

import com.moment.the.dto.AuthDto;
import com.moment.the.dto.AuthEditDto;
import com.moment.the.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/approve")
    public void approve(@RequestBody AuthDto authDto){
        authService.approveEmail(authDto);
    }

    @PostMapping("/updateApprove")
    public void updateApprove(@RequestBody AuthEditDto authEditDto){
        authService.editEmail(authEditDto);
    }
}
