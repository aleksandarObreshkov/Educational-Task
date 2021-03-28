package com.example.model;

import com.example.model.dto.HumanDTO;
import com.example.processor.Validate;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue(value = "human")
@JsonTypeName("human")
@Data
@Validate
public class Human extends Character {

    @OneToMany(cascade = CascadeType.ALL)
    private List<Starship> starships;

    @Override
    public String getCharacterType() {
        return "human";
    }

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
