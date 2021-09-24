package com.moment.the.config.mvc;

import com.moment.the.exceptionAdvice.ExceptionAdvice;
import com.moment.the.exceptionAdvice.exception.AccessTokenExpiredException;
import com.moment.the.exceptionAdvice.exception.InvalidTokenException;
import com.moment.the.exceptionAdvice.exception.UserNotFoundException;
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
        }catch(InvalidTokenException e){
            responseExceptionMessage(HttpStatus.BAD_REQUEST, response, exceptionAdvice.invalidToken(request, e));
        }catch(AccessTokenExpiredException e){
            responseExceptionMessage(HttpStatus.BAD_REQUEST, response, exceptionAdvice.accessTokenExpiredException(request, e));
        }catch (UserNotFoundException e){
            responseExceptionMessage(HttpStatus.BAD_REQUEST, response, exceptionAdvice.userNotFoundException(request, e));
        }catch (Exception e){
            log.error("알 수 없는 에러 발생", e);
            responseExceptionMessage(HttpStatus.INTERNAL_SERVER_ERROR, response, exceptionAdvice.defaultException(request, e));
        }
    }

    /**
     * filter에서 유저에게 Exception메시지를 전송하는 메서드
     * @param status HttpStatus
     * @param response HttpServletResponse
     * @param exceptionResult 해당 Exceptoin에 대한 Client에 반환할 정보를 가지고 있는 CommonResult객체
     * @author 정시원
     */
    private void responseExceptionMessage(HttpStatus status, HttpServletResponse response, CommonResult exceptionResult) {
        response.setStatus(status.value());
        response.setContentType(ContentType.APPLICATION_JSON.getMimeType());

        int exceptionCode = exceptionResult.getCode();
        String exceptionMsg = exceptionResult.getMsg();

        try {
            String exceptionResultToJson = responseService.getFailResultConvertString(exceptionCode, exceptionMsg); // CommonResult에 있는 값을 json으로 변환
            log.debug(
                    "filter에서 Exception 발생 원인: \n {}",
                    exceptionResultToJson
            );
            response.getWriter().write(exceptionResultToJson); // filter 단에서 client에 json를 보넨다.
        } catch (IOException e) {
            log.error("Filter에서 Error Response Json변환 실패", e);
            throw new RuntimeException(); // 알 수 없는 에러를 위해 일단 RuntimeException을 발생시킴
        }
    }
}
