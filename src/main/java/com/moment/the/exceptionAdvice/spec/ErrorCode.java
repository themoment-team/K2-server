package com.moment.the.exception.spec;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Error 코드를 전역에서 관리할 클래스
 *
 * @version 1.3
 * @author 전지환
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {
    BAD_REQUEST(400, "Bad Request",ErrorClassification.COMMON+"-ERR-400"),
    UNAUTHORIZED(401, "Unauthorized",ErrorClassification.COMMON+"-ERR-401"),
    FORBIDDEN(403,  "Forbidden",ErrorClassification.COMMON+"-ERR-403"),
    ;

    private int status;
    private String message;
    private String errorCode;
}
