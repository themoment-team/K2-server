package com.moment.the.exception.exception;

public class CustomMethodArgumentNotValidException extends RuntimeException{
    public CustomMethodArgumentNotValidException(String msg, Throwable t){
        super(msg, t);
    }
    public CustomMethodArgumentNotValidException(String msg){
        super(msg);
    }
    public CustomMethodArgumentNotValidException(){
        super();
    }
}
