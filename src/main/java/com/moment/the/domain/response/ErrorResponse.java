package com.moment.the.domain.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

    private String msg;
    private int code;
    private int status;

    public ErrorResponse(HttpStatus status, int code, String msg) {
        this.status = status.value();
        this.code = code;
        this.msg = msg;
    }

    public String convertToJson(){
        return "{" +
                "\"status\" : \""+ status +"\","+
                "\"code\" : \"" + code + "\","+
                "\"msg\" : \"" + msg + "\""+
                "}";
    }


}