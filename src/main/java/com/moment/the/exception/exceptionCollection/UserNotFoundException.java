package com.moment.the.exception.exceptionCollection;

import com.moment.the.exception.ErrorCode;
import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException{
    private final ErrorCode errorCode;

    public UserNotFoundException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
