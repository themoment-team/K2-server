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
    private boolean success = false;

    public ErrorResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String convertToJson(){
        return "{" +
                "\"success\" : "+ success +","+
                "\"code\" : " + code + ","+
                "\"msg\" : \"" + msg + "\""+
                "}";
    }


}