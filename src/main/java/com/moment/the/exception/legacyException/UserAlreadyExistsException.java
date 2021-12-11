package com.moment.the.exception.legacyException;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException(String msg, Throwable t){
        super(msg, t);
    }
    public UserAlreadyExistsException(String msg){
        super(msg);
    }
    public UserAlreadyExistsException(){
        super();
    }
}
