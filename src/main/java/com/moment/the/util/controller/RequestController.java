package com.moment.the.util.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/v1/request")
@RequiredArgsConstructor
public class RequestController {
    @GetMapping("/getIp")
    public String getReqIp(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String requestIp = request.getHeader("X-FORWARDED-FOR");
        if (requestIp == null){
            requestIp = request.getRemoteAddr();
        }
        return requestIp;
    }
}
