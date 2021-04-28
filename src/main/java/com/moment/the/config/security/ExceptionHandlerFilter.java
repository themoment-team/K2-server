package com.moment.the.config.security;

import com.moment.the.advice.exception.AccessTokenExpiredException;
import com.moment.the.domain.response.ErrorResponse;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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

    private final MessageSource messageSource;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(req, res);
        }catch(AccessTokenExpiredException e){
            log.error("[Exception] AccessTokenExpired");
            setErrorResponse(HttpStatus.BAD_REQUEST, res, "access-token-expired");
        }
    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse res, String getExceptionName){
        res.setStatus(status.value());
        res.setContentType("application/json");

        int exceptionCode = Integer.valueOf(getMessage(getExceptionName+".code"));
        String exceptionMsg = getMessage(getExceptionName+".msg");
        ErrorResponse errorResponse = new ErrorResponse(status, exceptionCode, exceptionMsg);
        try{
            String json = errorResponse.convertToJson();
            System.out.println(json);
            res.getWriter().write(json);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private String getMessage(String code, Object[] args){
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

    private String getMessage(String code){
        return getMessage(code, null);
    }
}
