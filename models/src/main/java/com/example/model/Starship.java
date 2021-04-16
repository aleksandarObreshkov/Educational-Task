package com.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.example.processor.Validate;

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

    // TODO You should always override hashCode() when you override equals():
    // https://www.baeldung.com/java-equals-hashcode-contracts
    @Override
    public boolean equals(Object obj) {
        if (!Starship.class.equals(obj.getClass())) {
            return false;
        }
        Starship starshipToCompare = (Starship) obj;
        return this.getName().equals(starshipToCompare.getName());
    }

}
