package com.moment.the.exception.handler;

import com.moment.the.exception.ErrorCode;
import com.moment.the.exception.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 *  {@link GlobalExceptionHandler}에서 핸들링 되지 않거나, 그 밖에 예외 핸들러에서 처리되지 않은 예외를 "알 수 없는 에러"로 핸들링하는 클래스
 *
 * @author 정시원
 * @version 1.3.1
 * @since 1.3.1
 */
@Slf4j
@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE) // 예외 처리가 되지 않은 예외를 핸들링해야 하므로 우선순위가 낮다.
public class UnknownErrorHandler {

    /**
     * 알 수 없는 에러 핸들링 메소드
     *
     * @param ex
     * @return ResponseEntity
     */
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(Exception ex){
        log.error("UnknownError", ex);
        ErrorResponse response = new ErrorResponse(ErrorCode.UNKNOWN_ERROR);

        return new ResponseEntity<>(response, HttpStatus.valueOf(ErrorCode.UNKNOWN_ERROR.getStatus()));
    }
}
