package com.litevar.opentool.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.litevar.opentool.util.JsonUtil;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class OpenTool {
    @JsonProperty("opentool")
    private String opentool;

    @JsonProperty("info")
    private Info info;

    @JsonProperty("functions")
    private List<Function> functions;

    @JsonProperty("schemas")
    private Map<String, Schema> schemas;

    public OpenTool() {
    }

    public static OpenTool fromJsonString(String jsonString) throws JsonProcessingException {
        return JsonUtil.fromJsonString(jsonString, OpenTool.class);
    }

    public String toJsonString() throws JsonProcessingException {
        return JsonUtil.toJsonString(this);
    }

    public String getOpentool() {
        return opentool;
    }

    public void setOpentool(String opentool) {
        this.opentool = opentool;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public List<Function> getFunctions() {
        return functions;
    }

    public void setFunctions(List<Function> functions) {
        this.functions = functions;
    }

    public Map<String, Schema> getSchemas() {
        return schemas;
    }

    public void setSchemas(Map<String, Schema> schemas) {
        this.schemas = schemas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OpenTool openTool = (OpenTool) o;
        return Objects.equals(opentool, openTool.opentool) &&
                Objects.equals(info, openTool.info) &&
                Objects.equals(functions, openTool.functions) &&
                Objects.equals(schemas, openTool.schemas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(opentool, info, functions, schemas);
    }

    @Override
    public String toString() {
        return "OpenTool{" +
                "opentool='" + opentool + '\'' +
                ", info=" + info +
                ", functions=" + functions +
                ", schemas=" + schemas +
                '}';
    }
}