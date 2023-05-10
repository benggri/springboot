package com.benggri.springboot.exception;

public class InternalServerErrorException extends RuntimeException {

    public InternalServerErrorException(String msg, Throwable t) {
        super(msg, t);
    }
    public InternalServerErrorException(String msg) {
        super(msg);
    }
    public InternalServerErrorException() {
        super();
    }

}
