package com.litevar.opentool.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.litevar.opentool.util.JsonUtil;

import java.util.HashMap;
import java.util.Map;

public abstract class OpenToolException extends RuntimeException {

    protected final int code;
    protected final String message;

    public OpenToolException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public OpenToolException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put("code", code);
        json.put("message", message);
        return json;
    }

    public String toJsonString() {
        try {
            return JsonUtil.toJsonString(toJson());
        } catch (JsonProcessingException e) {
            return String.format("{\"code\":%d,\"message\":\"%s\"}", code, message);
        }
    }
}