package com.example.app.domain;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

public class Character {

    private String id;
    private String name;
    private int age;
    private boolean forceUser;

    public Character(){}

    public Character(String id, String name, int age, boolean forceUser) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.forceUser = forceUser;
    }

    public String getId() {
        return id;
    }

    @JsonSetter
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @JsonSetter
    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    @JsonSetter
    public void setAge(int age) {
        this.age = age;
    }

    public boolean isForceUser() {
        return forceUser;
    }

    @JsonSetter
    public void setForceUser(boolean forceUser) {
        this.forceUser = forceUser;
    }

    @Override
    public String toString() {
        return "Character{" +
                " name='" + name + '\'' +
                ", age=" + age +
                ", forceUser=" + forceUser +
                '}';
    }
}
