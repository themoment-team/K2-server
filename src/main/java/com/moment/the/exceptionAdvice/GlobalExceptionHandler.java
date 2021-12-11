package com.moment.the.exception;

import com.moment.the.exception.newException.UserAlreadyExistsException;
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
}
