package com.litevar.opentool.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.litevar.opentool.util.JsonUtil;

import java.util.Objects;

public class Version {
    @JsonProperty("version")
    private String version;

    public Version() {
    }

    public Version(String version) {
        this.version = version;
    }

    public static Version fromJsonString(String jsonString) throws JsonProcessingException {
        return JsonUtil.fromJsonString(jsonString, Version.class);
    }

    public String toJsonString() throws JsonProcessingException {
        return JsonUtil.toJsonString(this);
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
        Version version1 = (Version) o;
        return Objects.equals(version, version1.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(version);
    }

    @Override
    public String toString() {
        return "Version{" +
                "version='" + version + '\'' +
                '}';
    }
}