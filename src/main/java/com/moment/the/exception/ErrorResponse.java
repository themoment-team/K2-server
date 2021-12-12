package com.moment.the.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * client에게 반환할 payload 클래스
 *
 * @version 1.3
 * @author 전지환
 */
@Getter
@Setter
public class ErrorResponse {
    private int status;
    private String message;
    private String code;

    public ErrorResponse(ErrorCode errorCode) {
        this.status = errorCode.getStatus();
        this.message = errorCode.getMessage();
        this.code = errorCode.getErrorCode();
    }
}
