package com.moment.the.controller;

import com.moment.the.config.security.JwtUtil;
import com.moment.the.domain.AdminDomain;
import com.moment.the.domain.response.CommonResult;
import com.moment.the.domain.response.ResponseService;
import com.moment.the.dto.AdminDto;
import com.moment.the.dto.SignInDto;
import com.moment.the.repository.AdminRepository;
import com.moment.the.service.AuthService;
import com.moment.the.service.AuthServiceImpl;
import com.moment.the.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class AdminController {
    private final AuthService authService;
    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;
    private final ResponseService responseService;

    @PostMapping("/login")
    public CommonResult login(@RequestBody SignInDto signInDto) throws Exception {
        final AdminDomain adminDomain = authService.loginUser(signInDto.getAdminId(), signInDto.getAdminPwd());
        final String token = jwtUtil.generateToken(adminDomain);
        final String refreshJwt = jwtUtil.generateRefreshToken(adminDomain);
        redisUtil.setDataExpire(adminDomain.getAdminId(), refreshJwt, JwtUtil.REFRESH_TOKEN_VALIDATION_SECOND);
        return responseService.getSuccessResult();
    }

    @PostMapping("/signup")
    public CommonResult signup(@RequestBody AdminDto adminDto){
        authService.signUp(adminDto);
        return responseService.getSuccessResult();
    }
}
