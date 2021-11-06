package com.moment.the.util.controller;

import com.moment.the.util.HttpRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @since 1.3.1
 * @author 전지환
 */
@RestController
@RequestMapping("/v1/request")
public class HttpRequestController {
    /**
     * 임의적으로 HttpRequestUtils.getClientIpAddressIfServletRequestExist() 호출합니다.
     * @return ip
     */
    @GetMapping("/getIp")
    public String getRequestIp(){
        return HttpRequestUtils.getClientIpAddressIfServletRequestExist();
    }
}
