package com.example.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.example.model.dto.DroidDTO;
import com.example.model.dto.HumanDTO;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

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

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name="character_movies",
            joinColumns=
            @JoinColumn(name="movie", referencedColumnName="id"),
            inverseJoinColumns=
            @JoinColumn(name="character", referencedColumnName="id")
    )
    private List<Movie> appearsIn;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name="character_friends",
            joinColumns=
            @JoinColumn(name="character", referencedColumnName="id")
    )
    private List<Character> friends;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Character)) return false;
        Character character = (Character) o;
        return isForceUser() == character.isForceUser() && Objects.equals(getId(), character.getId()) && getName().equals(character.getName()) && getAge().equals(character.getAge()) && Objects.equals(getAppearsIn(), character.getAppearsIn()) && Objects.equals(getFriends(), character.getFriends());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getAge(), isForceUser(), getAppearsIn(), getFriends());
    }
}
