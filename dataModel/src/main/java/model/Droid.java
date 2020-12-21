package model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue(value = "droid")
@JsonTypeName("droid")
@Data
public class Droid extends Character{

    //isn't this redundant?
    //won't it inherit the properties of the Character::id field?
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @NotNull(message = "Please specify the primary function of the droid.")
    private String primaryFunction;

    public Droid(String name, int age, boolean forceUser, String primaryFunction) {
        super(name, age, forceUser);
        this.primaryFunction = primaryFunction;
    }

    @Override
    public String getCharacterType() {
        return "droid";
    }

    public Droid() {
        super();
    }

    //toString, equals, hashCode

}
