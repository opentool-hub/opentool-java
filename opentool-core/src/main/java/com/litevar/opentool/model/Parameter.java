package com.litevar.opentool.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Parameter {
    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("schema")
    private Schema schema;

    @JsonProperty("required")
    private boolean required;

    public Parameter() {
    }

    public Parameter(String name, String description, Schema schema, boolean required) {
        this.name = name;
        this.description = description;
        this.schema = schema;
        this.required = required;
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

    public Schema getSchema() {
        return schema;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parameter parameter = (Parameter) o;
        return required == parameter.required &&
                Objects.equals(name, parameter.name) &&
                Objects.equals(description, parameter.description) &&
                Objects.equals(schema, parameter.schema);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, schema, required);
    }

    @Override
    public String toString() {
        return "Parameter{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", schema=" + schema +
                ", required=" + required +
                '}';
    }
}