package com.moment.the.exception.exception;

public class NoCommentException extends RuntimeException{
    public NoCommentException(String msg, Throwable t){
        super(msg, t);
    }
    public NoCommentException(String msg){
        super(msg);
    }
    public NoCommentException(){
        super();
    }
}
