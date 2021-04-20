package com.moment.the.controller.release;

import com.moment.the.config.security.JwtUtil;
import com.moment.the.domain.AdminDomain;
import com.moment.the.domain.response.CommonResult;
import com.moment.the.domain.response.ResponseService;
import com.moment.the.domain.response.SingleResult;
import com.moment.the.dto.AdminDto;
import com.moment.the.dto.SignInDto;
import com.moment.the.service.AuthService;
import com.moment.the.util.RedisUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
@ComponentScan(basePackages = {"com.moment.the.service"})
public class AdminController {
    private final AuthService authService;
    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;
    private final ResponseService responseService;

    @PostMapping("/login")
    public SingleResult<Map<String, String>> login(@Valid @RequestBody SignInDto signInDto) throws Exception {
        final AdminDomain adminDomain = authService.loginUser(signInDto.getAdminId(), signInDto.getAdminPwd());
        final String token = jwtUtil.generateAccessToken(adminDomain.getAdminId());
        final String refreshJwt = jwtUtil.generateRefreshToken(adminDomain.getAdminId());

        redisUtil.setDataExpire(adminDomain.getUsername(), refreshJwt, jwtUtil.REFRESH_TOKEN_VALIDATION_SECOND);
        Map<String ,String> map = new HashMap<>();
        map.put("id", adminDomain.getAdminId());
        map.put("accessToken", token); // accessToken 반환
        map.put("refreshToken", refreshJwt); // refreshToken 반환

        return responseService.getSingleResult(map);
    }

    @PostMapping("/logout")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    public CommonResult logout(){
        authService.logout();
        return responseService.getSuccessResult();
    }

    @PostMapping("/signup")
    public CommonResult signup(@Valid @RequestBody AdminDto adminDto) throws Exception {
        authService.signUp(adminDto);
        return responseService.getSuccessResult();
    }

    @PostMapping("/withdrawal")
    public CommonResult withdrawal(@Valid @RequestBody SignInDto signInDto) throws Exception {
        authService.withdrawal(signInDto);
        return responseService.getSuccessResult();
    }
}
