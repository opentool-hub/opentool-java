package com.litevar.opentool.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.litevar.opentool.util.JsonUtil;

import java.util.Map;
import java.util.Objects;

public class ToolReturn {
    @JsonProperty("id")
    private String id;

    @JsonProperty("result")
    private Map<String, Object> result;

    public ToolReturn() {
    }

    public ToolReturn(String id, Map<String, Object> result) {
        this.id = id;
        this.result = result;
    }

    public static ToolReturn fromJson(Map<String, Object> json) {
        return JsonUtil.fromMap(json, ToolReturn.class);
    }

    public static ToolReturn fromJsonString(String jsonString) throws JsonProcessingException {
        return JsonUtil.fromJsonString(jsonString, ToolReturn.class);
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

    public Map<String, Object> getResult() {
        return result;
    }

    public void setResult(Map<String, Object> result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ToolReturn that = (ToolReturn) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(result, that.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, result);
    }

    @Override
    public String toString() {
        return "ToolReturn{" +
                "id='" + id + '\'' +
                ", result=" + result +
                '}';
    }
}