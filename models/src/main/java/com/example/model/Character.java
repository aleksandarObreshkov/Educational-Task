package com.example.model;

import com.example.model.dto.DroidDTO;
import com.example.model.dto.HumanDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.ArrayList;
import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "characterType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Human.class),
        @JsonSubTypes.Type(value = Droid.class),
        @JsonSubTypes.Type(value = HumanDTO.class),
        @JsonSubTypes.Type(value = DroidDTO.class)})
@Entity
@Table
@Inheritance( strategy = InheritanceType.SINGLE_TABLE )
@DiscriminatorColumn( name = "character_type" )
public abstract class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @NotNull(message = "Please provide a name.")
    @Column(unique = true, name = "name")
    private String name;

    @NotNull(message = "Please provide age.")
    @PositiveOrZero(message = "Age must be positive.")
    @Column(name = "age")
    private Integer age;

    @Column(name = "force_user")
    private boolean forceUser;

    @JsonIgnore
    public abstract String getCharacterType();

    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Movie> appearsIn = new ArrayList<>();

    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Character> friends = new ArrayList<>();

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public boolean isForceUser() {
        return forceUser;
    }

    public void setForceUser(boolean forceUser) {
        this.forceUser = forceUser;
    }

    public List<Movie> getAppearsIn() {
        return appearsIn;
    }

    public void setAppearsIn(List<Movie> appearsIn) {
        this.appearsIn = appearsIn;
    }

    public List<Character> getFriends() {
        return friends;
    }

    public void setFriends(List<Character> friends) {
        this.friends = friends;
    }

    @Override
    public boolean equals(Object obj) {
        //This is context validation in that the objects represent characters in the StarWars movies.
        //Therefore, I assume that we can differentiate two characters even by only their name.
        //Furthermore, the database uses the same validation (the column name being UNIQUE CONSTRAINT).
        if (!Character.class.isAssignableFrom(obj.getClass())){
            return false;
        }
        Character characterToCompare = (Character) obj;
        return this.getName().equals(characterToCompare.getName());

    }
}
