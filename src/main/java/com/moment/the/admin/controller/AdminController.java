package com.moment.the.admin.controller;


import com.moment.the.admin.dto.AdminDto;
import com.moment.the.admin.dto.SignInDto;
import com.moment.the.admin.service.AdminService;
import com.moment.the.response.ResponseService;
import com.moment.the.response.result.CommonResult;
import com.moment.the.response.result.SingleResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    @ResponseStatus(HttpStatus.OK)
    public SingleResult<Map<String, String>> login(@Valid @RequestBody SignInDto signInDto) throws Exception {
        return responseService.getSingleResult(adminService.login(signInDto.getEmail(), signInDto.getPassword()));
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
    })
    public CommonResult logout(){
        adminService.logout();
        return responseService.getSuccessResult();
    }

    @PostMapping("/join")
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResult join(@Valid @RequestBody AdminDto adminDto) throws Exception {
        adminService.join(adminDto);
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
