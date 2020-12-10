package model;

import com.fasterxml.jackson.annotation.JsonSetter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.UUID;

public class Starship {

    private String id;

    @NotNull(message = "Please provide a name.")
    private String name;

    @Positive
    private float length;

    public Starship(){}

    public Starship(String name, float length) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.length = length;
    }

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

    @Override
    public String toString() {
        return "Starship{" +
                " name='" + name + '\'' +
                ", length=" + length +
                '}';
    }
}
