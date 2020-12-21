package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

//new line pls :D
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "characterType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Human.class),
        @JsonSubTypes.Type(value = Droid.class)})
@Entity
@Table
@Inheritance( strategy = InheritanceType.SINGLE_TABLE )
@DiscriminatorColumn( name = "characterType" )
@Data
public abstract class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(nullable = false)
    private Long id;

    @NotNull(message = "Please provide a name.")
    private String name;

    @PositiveOrZero(message = "Age must be positive.")
    private int age;

    private boolean forceUser;

    @JsonIgnore
    public abstract String getCharacterType();

    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Movie> appearsIn;

    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Character> friends;

    public Character(String name, int age, boolean forceUser) {
        this.name = name;
        this.age = age;
        this.forceUser = forceUser;
    }

    public Character() {
    }

    @Override
    public String toString() {
        return "Character{" +
                " name='" + name + '\'' +
                ", age=" + age +
                ", forceUser=" + forceUser +
                '}';
    }
    //what about equals and hashCode?
}
