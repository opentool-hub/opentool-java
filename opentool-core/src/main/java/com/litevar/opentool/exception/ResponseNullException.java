package com.litevar.opentool.exception;

public class ResponseNullException extends OpenToolException {

    public ResponseNullException(int code, String message) {
        super(code, message != null ? message : "Response is null");
    }

    public ResponseNullException() {
        this(500, "Response is null");
    }
}