package com.example.model;

import java.util.List;

import javax.persistence.*;

import com.example.model.dto.HumanDTO;
import com.example.processor.Validate;
import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue(value = "human")
@JsonTypeName("human")
@Data
@Validate
public class Human extends Character {

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="character_starships",
            joinColumns=
            @JoinColumn(name="character", referencedColumnName="id"),
            inverseJoinColumns =
            @JoinColumn(name = "starship", referencedColumnName = "id")
    )
    private List<Starship> starships;

    public List<Starship> getStarships() {
        return starships;
    }

    public void setStarships(List<Starship> starships) {
        this.starships = starships;
    }

    public static Human parseHuman(HumanDTO dto){
        Human result = new Human();
        result.setName(dto.getName());
        result.setAge(dto.getAge());
        result.setForceUser(dto.isForceUser());
        return result;
    }

}
