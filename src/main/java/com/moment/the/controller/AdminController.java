package com.moment.the.controller;

import com.moment.the.domain.AdminDomain;
import com.moment.the.dto.AdminDto;
import com.moment.the.service.AuthService;
import com.moment.the.util.CookieUtil;
import com.moment.the.util.JwtUtil;
import io.swagger.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1")
public class AdminController {
    @Autowired
    private AuthService authService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CookieUtil cookieUtil;

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
    @PostMapping("/login")
    public Response login(@RequestBody AdminDto user,
                          HttpServletRequest req,
                          HttpServletResponse res){
        try {
            final AdminDomain adminDomain = authService.loginUser(user.getAdminId(), user.getAdminPwd());
            final String token = jwtUtil.generateToken(adminDomain);
            final String refreshJwt = jwtUtil.generateRefreshToken(adminDomain);
            Cookie accessToken = cookieUtil.createCookie(JwtUtil.ACCESS_TOKEN_NAME, token);
            Cookie refreshToken = cookieUtil.createCookie(JwtUtil.REFRESH_TOKEN_NAME, refreshJwt);
            //Redis here
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
