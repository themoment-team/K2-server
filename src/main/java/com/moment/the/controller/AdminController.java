package com.moment.the.controller;

import com.moment.the.config.security.JwtUtil;
import com.moment.the.domain.AdminDomain;
import com.moment.the.dto.SignInDto;
import com.moment.the.repository.AdminRepository;
import com.moment.the.service.AuthService;
import com.moment.the.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class AdminController {
    private final AdminRepository adminRepository;
    private final AuthService authService;
    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;
    @PostMapping("/login")
    public String login(@RequestBody SignInDto signInDto,
                        HttpServletResponse response,
                        HttpServletRequest request) throws Exception {
        try {
            final AdminDomain adminDomain = authService.loginUser(signInDto.getAdminId(), signInDto.getAdminPwd());
            final String token = jwtUtil.generateToken(adminDomain);
            final String refreshJwt = jwtUtil.generateRefreshToken(adminDomain);
            redisUtil.setDataExpire(adminDomain.getAdminId(), refreshJwt, JwtUtil.REFRESH_TOKEN_VALIDATION_SECOND);

        }catch (Exception e){

        }
        return "";
    }

}
