package com.moment.the.exceptionAdvice;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorCode {
    BAD_REQUEST(400, ErrorClassification.COMMON+"-ERR-400", "Bad Request"),
    UNAUTHORIZED(401, ErrorClassification.COMMON+"-ERR-401", "Unauthorized"),
    FORBIDDEN(403, ErrorClassification.COMMON+"-ERR-403", "Forbidden"),
    ;

    private int status;
    private String errorCode;
    private String message;
}
