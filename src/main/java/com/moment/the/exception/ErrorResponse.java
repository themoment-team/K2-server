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
    private String message;
    private String details;

    public ErrorResponse(ErrorCode errorCode) {
        this.message = errorCode.getMessage();
        this.details = errorCode.getDetails();
    }
}
