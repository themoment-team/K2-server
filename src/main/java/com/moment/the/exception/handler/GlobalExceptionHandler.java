package com.moment.the.exception.handler;

import com.moment.the.exception.ErrorResponse;
import com.moment.the.exception.exceptionCollection.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Exception 발생시 전역으로 처리할 exception handler
 *
 * @version 1.3
 * @author 전지환, 조재영, 양시준
 */
@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE) // 전역 예외 핸들러이므로 우선순위가 높다.
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

    /**
     * handleAccessNotFoundException 핸들링 메소드
     *
     * @param ex AccessNotFoundException
     * @return ResponseEntity
     */
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleAccessNotFoundException(AccessNotFoundException ex){
        log.error("Access Not FoundException",ex);
        ErrorResponse response = new ErrorResponse(ex.getErrorCode());

        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    /**
     * handleNoPostException 핸들링 메소드
     *
     * @param ex NoPostException
     * @return ResponseEntity
     */
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleNoPostException(NoPostException ex){
        log.error("No Post Exception",ex);
        ErrorResponse response = new ErrorResponse(ex.getErrorCode());

        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    /**
     * handleNoCommentException 핸들링 메소드
     *
     * @param ex NoCommentException
     * @return ResponseEntity
     */
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleNoCommentException(NoCommentException ex){
        log.error("No Comment Exception",ex);
        ErrorResponse response = new ErrorResponse(ex.getErrorCode());

        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    /**
     * handleAccessTokenExpiredException 핸들링 메소드
     *
     * @param ex AccessTokenExpiredException
     * @return ResponseEntity
     */
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleAccessTokenExpiredException(AccessTokenExpiredException ex){
        log.error("AccessTokenExpiredException",ex);
        ErrorResponse response = new ErrorResponse(ex.getErrorCode());

        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    /**
     * handleAnswerAlreadyExistsException 핸들링 메소드
     *
     * @param ex AnswerAlreadyExistsException
     * @return ResponseEntity
     */
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleAnswerAlreadyExistsException(AnswerAlreadyExistsException ex){
        log.error("AnswerAlreadyExistsException",ex);
        ErrorResponse response = new ErrorResponse(ex.getErrorCode());

        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    /**
     * handleInvalidTokenException 핸들링 메소드
     *
     * @param ex InvalidTokenException
     * @return ResponseEntity
     */
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleInvalidTokenException(InvalidTokenException ex){
        log.error("InvalidTokenException",ex);
        ErrorResponse response = new ErrorResponse(ex.getErrorCode());

        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }
    /**
     * handleUserNotFoundException 핸들링 메소드
     *
     * @param ex UserNotFoundException
     * @return ResponseEntity
     */
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex){
        log.error("UserNotFoundException",ex);
        ErrorResponse response = new ErrorResponse(ex.getErrorCode());

        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }
}
