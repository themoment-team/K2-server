package com.moment.the.exception.handler;

import com.moment.the.exception.ErrorResponse;
import com.moment.the.exception.exceptionCollection.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Exception 발생시 전역으로 처리할 exception handler
 *
 * @version 1.3
 * @author 전지환
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * UserAlreadyExistsException 핸들링 메소드
     *
     * @param ex UserAlreadyExistsException
     * @return ResponseEntity
     */
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex){
        log.error("UserAlreadyExistsException", ex);
        ErrorResponse response = new ErrorResponse(ex.getErrorCode());

        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleAccessNotFoundException(AccessNotFoundException ex){
        log.error("AccessNotFoundException",ex);
        ErrorResponse response = new ErrorResponse(ex.getErrorCode());

        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleAccessTokenExpiredException(AccessTokenExpiredException ex){
        log.error("AccessTokenExpiredException",ex);
        ErrorResponse response = new ErrorResponse(ex.getErrorCode());

        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleAnswerAlreadyExistsException(AnswerAlreadyExistsException ex){
        log.error("AnswerAlreadyExistsException",ex);
        ErrorResponse response = new ErrorResponse(ex.getErrorCode());

        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleInvalidTokenException(InvalidTokenException ex){
        log.error("InvalidTokenException",ex);
        ErrorResponse response = new ErrorResponse(ex.getErrorCode());

        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }
}
