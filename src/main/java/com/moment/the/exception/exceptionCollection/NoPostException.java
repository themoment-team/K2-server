package com.moment.the.exception.exceptionCollection;

import com.moment.the.exception.ErrorCode;
import lombok.Getter;

@Getter
public class NoPostException extends RuntimeException{
    private final ErrorCode errorCode;

    public NoPostException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
