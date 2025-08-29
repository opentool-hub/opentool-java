package com.litevar.opentool.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class Function {
    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("parameters")
    private List<Parameter> parameters;

    @JsonProperty("return")
    private Return returnValue;

    public Function() {
    }

    public Function(String name, String description, List<Parameter> parameters, Return returnValue) {
        this.name = name;
        this.description = description;
        this.parameters = parameters;
        this.returnValue = returnValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public Return getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(Return returnValue) {
        this.returnValue = returnValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Function function = (Function) o;
        return Objects.equals(name, function.name) &&
                Objects.equals(description, function.description) &&
                Objects.equals(parameters, function.parameters) &&
                Objects.equals(returnValue, function.returnValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, parameters, returnValue);
    }

    @Override
    public String toString() {
        return "Function{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", parameters=" + parameters +
                ", returnValue=" + returnValue +
                '}';
    }
}