package com.benggri.springboot.exception;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String msg, Throwable t) {
        super(msg, t);
    }
    public UnauthorizedException(String msg) {
        super(msg);
    }
    public UnauthorizedException() {
        super();
    }

}
