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
    @Column(unique = true)
    private String name;

    @Positive
    @NotNull(message = "Please specify the length of the ship.")
    // TODO Just rename the field to "lengthInMeters" and you're fine. Feet are an incredibly stupid unit for measurement anyway.
    // However, let's say that in the future some arsehole asks for imperial units. You can still keep this field as "lengthInMeters".
    // The only thing you'd have to do is to have another option (--imperial) in the CLI and just multiply this field by some constant
    // before showing the value to the user.
    // By the way "lengthInMeters" might sound stupid, but it's a much better name than just "length", because there's no ambiguity
    // about the unit of measurement.
    // Also, props for thinking about this.
    private Float length;//either in feet or meters //TODO and how will this be distinguished?

    // TODO
    public Starship(){}

    // TODO Having a constructor here and not in the other model classes is inconsistent. Imagine yourself as another developer that
    // is supposed to use this code to add some new feature to the application.
    // You try to construct a starship - cool, there's a constructor. You then try to construct a droid - wait... how do I do this?
    // Where's the constructor?
    //
    // Either have constructors in every model or in neither. Personally, I prefer using builders (Lombok can generate them for you),
    // because it looks decent regardless of the number of fields.
    public Starship(String name, float length) {
        this.name = name;
        this.length = length;
    }

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

    public Float getLength() {
        return length;
    }

    public void setLength(Float length) {
        this.length = length;
    }
}
