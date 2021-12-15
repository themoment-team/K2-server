package com.moment.the.exception.exceptionCollection;

import com.moment.the.exception.ErrorCode;
import lombok.Getter;

@Getter
public class NoCommentException extends RuntimeException{
    private final ErrorCode errorCode;

    public NoCommentException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
