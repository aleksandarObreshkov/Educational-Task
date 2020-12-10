package model;
import com.fasterxml.jackson.annotation.JsonSetter;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.UUID;

public class Character {

    private String id;

    @NotNull(message = "Please provide a name.")
    private String name;

    @PositiveOrZero(message = "Age must be positive.")
    private int age;

    private boolean forceUser;

    public Character(){}

    public Character(String name, int age, boolean forceUser) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.age = age;
        this.forceUser = forceUser;
    }

    public Character(String id, String name, int age, boolean forceUser) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.forceUser = forceUser;
    }

    public String getId() {
        return id;
    }

    @JsonSetter
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @JsonSetter
    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    @JsonSetter
    public void setAge(int age) {
        this.age = age;
    }

    public boolean isForceUser() {
        return forceUser;
    }

    @JsonSetter
    public void setForceUser(boolean forceUser) {
        this.forceUser = forceUser;
    }

    @Override
    public String toString() {
        return "Character{" +
                " name='" + name + '\'' +
                ", age=" + age +
                ", forceUser=" + forceUser +
                '}';
    }
}
