package com.coderlong.exception;

public class ProtocolException extends RuntimeException{
    public ProtocolException(String message) {
        super(message);
    }

    public ProtocolException(String message, Throwable cause) {
        super(message, cause);
    }

}
