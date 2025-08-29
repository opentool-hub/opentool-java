package com.litevar.opentool.exception;

public class ErrorNullException extends OpenToolException {

    public ErrorNullException(int code, String message) {
        super(code, message != null ? message : "Error is null");
    }

    public ErrorNullException() {
        this(500, "Error is null");
    }
}