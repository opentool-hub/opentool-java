package com.litevar.opentool.exception;

public class OpenToolServerNoAccessException extends OpenToolException {

    public OpenToolServerNoAccessException(int code, String message) {
        super(code, message != null ? message : "Please check OpenTool Server is RUNNING or NOT");
    }

    public OpenToolServerNoAccessException() {
        this(404, "Please check OpenTool Server is RUNNING or NOT");
    }
}