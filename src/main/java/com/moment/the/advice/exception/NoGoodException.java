package com.moment.the.advice.exception;

public class NoGoodException extends RuntimeException{
    public NoGoodException(String msg, Throwable t){
        super(msg, t);
    }
    public NoGoodException(String msg){
        super(msg);
    }
    public NoGoodException(){
        super();
    }
}
