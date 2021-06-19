package com.moment.the.exceptionAdvice.exception;

public class AnswerAlreadyExistsException extends RuntimeException{
    public AnswerAlreadyExistsException(String msg, Throwable t){
        super(msg, t);
    }
    public AnswerAlreadyExistsException(String msg){
        super(msg);
    }
    public AnswerAlreadyExistsException(){
        super();
    }
}
