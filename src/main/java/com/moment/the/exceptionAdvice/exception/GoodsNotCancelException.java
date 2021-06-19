package com.moment.the.exceptionAdvice.exception;

public class GoodsNotCancelException extends RuntimeException{
    public GoodsNotCancelException(String msg, Throwable t){
        super(msg, t);
    }
    public GoodsNotCancelException(String msg){
        super(msg);
    }
    public GoodsNotCancelException(){
        super();
    }
}
