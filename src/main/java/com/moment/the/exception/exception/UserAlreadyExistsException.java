package com.moment.the.exception.exception;

import com.moment.the.exception.ErrorCode;
import lombok.Getter;

/**
 * 사용자 정의 exception 클래스
 *
 * @version 1.3
 * @author 전지환
 */
@Getter
public class UserAlreadyExistsException extends RuntimeException{
    private final ErrorCode errorCode;

    public UserAlreadyExistsException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
