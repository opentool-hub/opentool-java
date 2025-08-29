package com.litevar.opentool.exception;

public class OpenToolServerUnauthorizedException extends OpenToolException {

    public OpenToolServerUnauthorizedException(int code, String message) {
        super(code, message != null ? message : "Please check API Key is VALID or NOT");
    }

    public OpenToolServerUnauthorizedException() {
        this(401, "Please check API Key is VALID or NOT");
    }
}