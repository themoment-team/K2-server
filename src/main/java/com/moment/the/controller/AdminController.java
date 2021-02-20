package com.moment.the.controller;

import com.moment.the.domain.AdminDomain;
import com.moment.the.service.AuthService;
import io.swagger.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1")
public class AdminController {
    @Autowired
    private AuthService authService;

    @PostMapping("/admin/signup")
    public Map<String, String> signUpUser(@RequestBody AdminDomain adminDomain){
        Map<String, String> map = new HashMap<>();
        try {
            authService.signUpUser(adminDomain);
            map.put("code", "200");
            map.put("msg", "성공");
        } catch (Exception e) {
            map.put("code", "400");
            map.put("msg", "실패");
        }
        return map;
    }
}
