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

    /**
     * Custom Exception 에 해당하는 ErrorResponse
     *
     * @param errorCode
     */
    public ErrorResponse(ErrorCode errorCode) {
        this.message = errorCode.getMessage();
        this.details = errorCode.getDetails();
    }

    /**
     * java, spring Exception 에 해당하는 ErrorResponse
     *
     * @param message
     * @param details
     */
    public ErrorResponse(String message, String details) {
        this.message = message;
        this.details = details;
    }
}
