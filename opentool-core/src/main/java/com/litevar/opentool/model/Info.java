package com.litevar.opentool.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Info {
    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("version")
    private String version;

    public Info() {
    }

    public Info(String title, String description, String version) {
        this.title = title;
        this.description = description;
        this.version = version;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Info info = (Info) o;
        return Objects.equals(title, info.title) &&
                Objects.equals(description, info.description) &&
                Objects.equals(version, info.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, version);
    }

    @Override
    public String toString() {
        return "Info{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}