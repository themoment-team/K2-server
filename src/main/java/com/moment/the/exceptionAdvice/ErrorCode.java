package com.moment.the.exceptionAdvice;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
