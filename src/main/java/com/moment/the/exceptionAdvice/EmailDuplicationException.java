package com.moment.the.exceptionAdvice;

import lombok.Getter;

/**
 * 사용자 정의 exception 클래스
 *
 * @version 1.3
 * @author 전지환
 */
@Getter
public class EmailDuplicationException extends RuntimeException{
    private final ErrorCode errorCode;

    public EmailDuplicationException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
