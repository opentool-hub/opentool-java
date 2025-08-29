package com.litevar.opentool.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.litevar.opentool.util.JsonUtil;

import java.util.Map;
import java.util.Objects;

public class JsonRpcResponse {
    @JsonProperty("jsonrpc")
    private String jsonrpc = "2.0";

    @JsonProperty("result")
    private Map<String, Object> result;

    @JsonProperty("error")
    private JsonRpcError error;

    @JsonProperty("id")
    private String id;

    public JsonRpcResponse() {
    }

    public static JsonRpcResponse fromJsonString(String jsonString) throws JsonProcessingException {
        return JsonUtil.fromJsonString(jsonString, JsonRpcResponse.class);
    }

    public String toJsonString() throws JsonProcessingException {
        return JsonUtil.toJsonString(this);
    }

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public Map<String, Object> getResult() {
        return result;
    }

    public void setResult(Map<String, Object> result) {
        this.result = result;
    }

    public JsonRpcError getError() {
        return error;
    }

    public void setError(JsonRpcError error) {
        this.error = error;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JsonRpcResponse that = (JsonRpcResponse) o;
        return Objects.equals(jsonrpc, that.jsonrpc) &&
                Objects.equals(result, that.result) &&
                Objects.equals(error, that.error) &&
                Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jsonrpc, result, error, id);
    }

    @Override
    public String toString() {
        return "JsonRpcResponse{" +
                "jsonrpc='" + jsonrpc + '\'' +
                ", result=" + result +
                ", error=" + error +
                ", id='" + id + '\'' +
                '}';
    }
}