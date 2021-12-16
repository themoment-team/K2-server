package com.moment.the.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moment.the.exception.ErrorCode;
import com.moment.the.exception.ErrorResponse;
import com.moment.the.exception.exceptionCollection.AccessTokenExpiredException;
import com.moment.the.exception.exceptionCollection.InvalidTokenException;
import com.moment.the.exception.exceptionCollection.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.http.entity.ContentType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * filter단에서 발생하는 Exception을 handling하는 클래스
 *
 * @since 1.0.0
 * @version 1.3.1
 * @author 정시원
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;
    
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
        }catch (InvalidTokenException e){
            responseErrorMessage(response, e.getErrorCode());
        }catch (AccessTokenExpiredException e){
            responseErrorMessage(response, e.getErrorCode());
        }catch (UserNotFoundException e){
            responseErrorMessage(response, e.getErrorCode());
        } catch (Exception e){
            log.error("알 수 없는 에러 발생", e);
//            responseExceptionMessage(HttpStatus.INTERNAL_SERVER_ERROR, response, exceptionAdvice.defaultException(request, e));
        }
    }

    /**
     * filter에서 유저에게 Exception메시지를 전송하는 메서드
     *
     * @param response HttpServletResponse
     * @param errorCode 사용자에게 반환할 에러의 정보를 담고 있는 객체
     * @author 정시원
     */
    private void responseErrorMessage(HttpServletResponse response, ErrorCode errorCode) {
        // content type, status code 세팅
        response.setContentType(ContentType.APPLICATION_JSON.getMimeType());
        response.setStatus(errorCode.getStatus());

        try {
            ErrorResponse errorResponse = new ErrorResponse(errorCode);
            String errorResponseEntityToJson = objectMapper.writeValueAsString(errorResponse);

            response.getWriter().write(errorResponseEntityToJson); // filter 단에서 client에 json를 보낸다.
        } catch (IOException e) {
            log.error("Filter에서 Error Response Json변환 실패", e);
            throw new RuntimeException(e); // 알 수 없는 에러를 위해 일단 RuntimeException을 발생시킴
        }
    }
}