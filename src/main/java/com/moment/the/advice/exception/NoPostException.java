package com.moment.the.advice.exception;

public class NoPostException extends RuntimeException{
    public NoPostException(String msg, Throwable t){
        super(msg, t);
    }
    public NoPostException(String msg){
        super(msg);
    }
    public NoPostException(){
        super();
    }
}
