package com.example.model;

import com.example.processor.Validate;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

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
    public boolean equals(Object obj) {
       if (!Starship.class.equals(obj.getClass())){
           return false;
       }
       Starship starshipToCompare = (Starship) obj;
       return this.getName().equals(starshipToCompare.getName());
    }
}
