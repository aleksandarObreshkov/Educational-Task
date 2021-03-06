package model;

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
public class Human extends Character{

    @OneToMany(cascade = CascadeType.ALL)
    private List<Starship> starships;

    @Override
    public String getCharacterType() {
        return "human";
    }

    public Human() {

    }

    //toString, equals, hashCode
}
