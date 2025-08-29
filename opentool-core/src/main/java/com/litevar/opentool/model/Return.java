package com.litevar.opentool.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Return {
    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("schema")
    private Schema schema;

    public Return() {
    }

    public Return(String name, String description, Schema schema) {
        this.name = name;
        this.description = description;
        this.schema = schema;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Return aReturn = (Return) o;
        return Objects.equals(name, aReturn.name) &&
                Objects.equals(description, aReturn.description) &&
                Objects.equals(schema, aReturn.schema);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, schema);
    }

    @Override
    public String toString() {
        return "Return{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", schema=" + schema +
                '}';
    }
}