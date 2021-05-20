package com.moment.the.config.mvc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class RequestResponseLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
        ContentCachingRequestWrapper reqWrapper = new ContentCachingRequestWrapper(req);
        ContentCachingResponseWrapper resWrapper = new ContentCachingResponseWrapper(res);

        long start = System.currentTimeMillis();
        chain.doFilter(reqWrapper, resWrapper);
        long end = System.currentTimeMillis();

        double runningTime = (end - start) / 1000.0;

        log.debug("[Runtime] : {} s", runningTime);
        log.info("\n" +
                        "[REQUEST] {} - {} {}\n" +
                        "Headers : {}\n" +
                        "Request : {}\n"
                ,
                req.getMethod(), req.getRequestURI(), resWrapper.getStatus(),
                getHeaders(req),
                getRequestBody(reqWrapper)

        );
    }

    private Map getHeaders(HttpServletRequest req){
        Map headerMap = new HashMap<>();

        Enumeration headerArray = req.getHeaderNames();
        while(headerArray.hasMoreElements()){
            String headerName = (String) headerArray.nextElement();
            headerMap.put(headerName, req.getHeader(headerName));
        }
        return headerMap;
    }

    private String getRequestBody(ContentCachingRequestWrapper req){
        ContentCachingRequestWrapper reqWrapper = WebUtils.getNativeRequest(req, ContentCachingRequestWrapper.class);
        if(reqWrapper != null){
            byte[] buf = reqWrapper.getContentAsByteArray();
            if(buf.length > 0){
                try{
                    return new String(buf, 0, buf.length, reqWrapper.getCharacterEncoding());
                }catch (UnsupportedEncodingException e){
                    return " - ";
                }
            }
        }
        return " - ";
    }


}
