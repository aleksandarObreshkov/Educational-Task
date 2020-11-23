package com.example.app.domain;

import com.fasterxml.jackson.annotation.JsonSetter;

public class Starship {

    private String id;
    private String name;
    private float length;

    public Starship(){}

    public Starship(String id, String name, float length) {
        this.id = id;
        this.name = name;
        this.length = length;
    }

    @JsonSetter
    public void setId(String id) {
        this.id = id;
    }
    @JsonSetter
    public void setName(String name) {
        this.name = name;
    }
    @JsonSetter
    public void setLength(float length) {
        this.length = length;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getLength() {
        return length;
    }
}
