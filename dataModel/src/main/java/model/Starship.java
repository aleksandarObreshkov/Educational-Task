package model;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
@Data
public class Starship {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @NotNull(message = "Please provide a name.")
    private String name;

    @Positive
    private float length;//either in feet or meters

    public Starship(){}

    public Starship(String name, float length) {
        this.name = name;
        this.length = length;
    }

    @Override
    public String toString() {
        return "Starship{" +
                " name='" + name + '\'' +
                ", length=" + length +
                '}';
    }

}
