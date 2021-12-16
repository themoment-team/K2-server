package com.moment.the.exception.exceptionCollection;

import com.moment.the.exception.ErrorCode;
import lombok.Getter;

/**
 * 사용자 정의 exception 클래스
 *
 * @version 1.3
 * @author 양시준
 */
@Getter
public class AnswerAlreadyExistsException extends RuntimeException {
    private final ErrorCode errorCode;

    public AnswerAlreadyExistsException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
