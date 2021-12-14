package com.moment.the.config.mvc;

import com.moment.the.exception.legacy.ExceptionAdvice;
import com.moment.the.exception.legacy.legacyException.AccessTokenExpiredException;
import com.moment.the.exception.legacy.legacyException.InvalidTokenException;
import com.moment.the.exception.legacy.legacyException.UserNotFoundException;
import com.moment.the.response.ResponseService;
import com.moment.the.response.result.CommonResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter에서 발생하는 Exception을 handling하는 클래스
 * @since 1.0.0
 * @version 1.1.2
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    private final ExceptionAdvice exceptionAdvice;
    private final ResponseService responseService;

    /**
     * filter에서 발생한 Exception을 catch한 후 사용자에게 예외 Response를 전달합니다.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param filterChain filterChain
     * @author 정시원
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            filterChain.doFilter(request, response);
        }catch (Exception e){
            log.error("알 수 없는 에러 발생", e);
//            responseExceptionMessage(HttpStatus.INTERNAL_SERVER_ERROR, response, exceptionAdvice.defaultException(request, e));
        }
    }
}