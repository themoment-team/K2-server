package com.moment.the.exceptionAdvice.exception;

public class NoImprovementException extends RuntimeException{
    public NoImprovementException(String msg, Throwable t){
        super(msg, t);
    }
    public NoImprovementException(String msg){
        super(msg);
    }
    public NoImprovementException(){
        super();
    }
}
