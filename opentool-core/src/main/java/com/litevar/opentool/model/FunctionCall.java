package com.litevar.opentool.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.litevar.opentool.util.JsonUtil;

import java.util.Map;
import java.util.Objects;

public class FunctionCall {
    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("arguments")
    private Map<String, Object> arguments;

    public FunctionCall() {
    }

    public FunctionCall(String id, String name, Map<String, Object> arguments) {
        this.id = id;
        this.name = name;
        this.arguments = arguments;
    }

    public static FunctionCall fromJson(Map<String, Object> json) {
        return JsonUtil.fromMap(json, FunctionCall.class);
    }

    public static FunctionCall fromJsonString(String jsonString) throws JsonProcessingException {
        return JsonUtil.fromJsonString(jsonString, FunctionCall.class);
    }

    public Map<String, Object> toJson() {
        return JsonUtil.toMap(this);
    }

    public String toJsonString() throws JsonProcessingException {
        return JsonUtil.toJsonString(this);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> getArguments() {
        return arguments;
    }

    public void setArguments(Map<String, Object> arguments) {
        this.arguments = arguments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FunctionCall that = (FunctionCall) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(arguments, that.arguments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, arguments);
    }

    @Override
    public String toString() {
        return "FunctionCall{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", arguments=" + arguments +
                '}';
    }
}