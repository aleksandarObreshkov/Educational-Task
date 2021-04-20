package com.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.example.processor.Validate;

import java.util.Objects;

@Validate
@Entity
public class Starship {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @NotNull(message = "Please provide a name.")
    @Column(unique = true, name = "name")
    private String name;

    @Positive
    @NotNull(message = "Please specify the length of the ship.")
    @Column(name = "length_in_meters")
    private Float lengthInMeters;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getLengthInMeters() {
        return lengthInMeters;
    }

    public void setLengthInMeters(Float length) {
        this.lengthInMeters = length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Starship)) return false;
        Starship starship = (Starship) o;
        return Objects.equals(getId(), starship.getId()) && getName().equals(starship.getName()) && getLengthInMeters().equals(starship.getLengthInMeters());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getLengthInMeters());
    }
}
