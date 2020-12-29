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

    @NotNull(message = "Please specify the primary function of the droid.")
    private String primaryFunction;

    @Override
    public String getCharacterType() {
        return "droid";
    }

    public Droid() {
        super();
    }

    //toString, equals, hashCode

}
