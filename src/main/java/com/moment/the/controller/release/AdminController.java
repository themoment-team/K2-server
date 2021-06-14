package com.moment.the.controller.release;

import com.moment.the.config.security.JwtUtil;
import com.moment.the.domain.AdminDomain;
import com.moment.the.domain.response.CommonResult;
import com.moment.the.domain.response.ResponseService;
import com.moment.the.domain.response.SingleResult;
import com.moment.the.dto.AdminDto;
import com.moment.the.dto.SignInDto;
import com.moment.the.service.AdminService;
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
    private final AdminService adminService;
    private final ResponseService responseService;

    @PostMapping("/login")
    public SingleResult<Map<String, String>> login(@Valid @RequestBody SignInDto signInDto) throws Exception {
        return responseService.getSingleResult(adminService.loginUser(signInDto.getAdminId(), signInDto.getAdminPwd()));
    }

    @PostMapping("/logout")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
    })
    public CommonResult logout(){
        adminService.logout();
        return responseService.getSuccessResult();
    }

    @PostMapping("/signup")
    public CommonResult signup(@Valid @RequestBody AdminDto adminDto) throws Exception {
        adminService.signUp(adminDto);
        return responseService.getSuccessResult();
    }

    @PostMapping("/withdrawal")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public CommonResult withdrawal(@Valid @RequestBody SignInDto signInDto) throws Exception {
        adminService.withdrawal(signInDto);
        return responseService.getSuccessResult();
    }
}
