package com.litevar.opentool.exception;

public class OpenToolServerCallException extends OpenToolException {

    public OpenToolServerCallException(String message) {
        super(500, message);
    }

    public OpenToolServerCallException(int code, String message) {
        super(code, message);
    }

    public OpenToolServerCallException(String message, Throwable cause) {
        super(500, message, cause);
    }
}