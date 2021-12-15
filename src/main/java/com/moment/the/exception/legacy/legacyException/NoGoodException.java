package com.moment.the.exception.legacy.legacyException;

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
