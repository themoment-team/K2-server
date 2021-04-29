package com.moment.the.config.security;

import com.moment.the.advice.ExceptionAdvice;
import com.moment.the.advice.exception.AccessTokenExpiredException;
import com.moment.the.advice.exception.InvalidTokenException;
import com.moment.the.domain.response.CommonResult;
import com.moment.the.domain.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    private final ExceptionAdvice exceptionAdvice;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(req, res);
        }catch(InvalidTokenException e){
            setExceptionRes(HttpStatus.BAD_REQUEST, res, exceptionAdvice.invalidToken(req, e));
        }catch(AccessTokenExpiredException e){
            setExceptionRes(HttpStatus.BAD_REQUEST, res, exceptionAdvice.accessTokenExpiredException(req, e));
        }catch (Exception e){
            setExceptionRes(HttpStatus.INTERNAL_SERVER_ERROR, res, exceptionAdvice.defaultException(req, e));
        }
    }

    //
    public void setExceptionRes(HttpStatus status, HttpServletResponse res, CommonResult exceptionResult){
        res.setStatus(status.value());
        res.setContentType("application/json");

        int exceptionCode = exceptionResult.getCode();
        String exceptionMsg = exceptionResult.getMsg();
        ErrorResponse errorResponse = new ErrorResponse(exceptionCode, exceptionMsg); // 생성자로 상태코드와 예외 code, 예외 msg 를 넘긴다.
        try{
            String json = errorResponse.convertToJson(); // errorResponse 에 있는 값을 json 으로 변환
            System.out.println(json);
            res.getWriter().write(json); // filter 단에서 client 에 json 을 보넨다.
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
