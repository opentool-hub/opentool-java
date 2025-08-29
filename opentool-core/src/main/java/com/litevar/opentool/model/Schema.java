package com.litevar.opentool.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Schema {
    @JsonProperty("type")
    private String type;

    @JsonProperty("description")
    private String description;

    @JsonProperty("properties")
    private Map<String, Schema> properties;

    @JsonProperty("items")
    private Schema items;

    @JsonProperty("enum")
    private List<String> enumValues;

    @JsonProperty("required")
    private List<String> required;

    public Schema() {
    }

    public Schema(String type) {
        this.type = type;
    }

    public Schema(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, Schema> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Schema> properties) {
        this.properties = properties;
    }

    public Schema getItems() {
        return items;
    }

    public void setItems(Schema items) {
        this.items = items;
    }

    public List<String> getEnumValues() {
        return enumValues;
    }

    public void setEnumValues(List<String> enumValues) {
        this.enumValues = enumValues;
    }

    public List<String> getRequired() {
        return required;
    }

    public void setRequired(List<String> required) {
        this.required = required;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schema schema = (Schema) o;
        return Objects.equals(type, schema.type) &&
                Objects.equals(description, schema.description) &&
                Objects.equals(properties, schema.properties) &&
                Objects.equals(items, schema.items) &&
                Objects.equals(enumValues, schema.enumValues) &&
                Objects.equals(required, schema.required);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, description, properties, items, enumValues, required);
    }

    @Override
    public String toString() {
        return "Schema{" +
                "type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", properties=" + properties +
                ", items=" + items +
                ", enumValues=" + enumValues +
                ", required=" + required +
                '}';
    }
}