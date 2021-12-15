package com.moment.the.exception.exceptionCollection;

import com.moment.the.exception.ErrorCode;
import lombok.Getter;

@Getter
public class CustomMethodArgumentNotValidException extends RuntimeException{
    private ErrorCode errorCode;

    public CustomMethodArgumentNotValidException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
