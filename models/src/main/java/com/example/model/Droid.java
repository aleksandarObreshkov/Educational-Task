package com.example.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import com.example.model.dto.DroidDTO;
import com.example.processor.Validate;
import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue(value = "droid")
@JsonTypeName("droid")
@Data
@Validate
public class Droid extends Character {

    @NotNull(message = "Please specify the primary function of the droid.")
    @Column(name = "primary_function")
    private String primaryFunction;

    public String getPrimaryFunction() {
        return primaryFunction;
    }

    public void setPrimaryFunction(String primaryFunction) {
        this.primaryFunction = primaryFunction;
    }

    public static Droid parseDroid(DroidDTO dto){
        Droid droid = new Droid();
        droid.setName(dto.getName());
        droid.setAge(dto.getAge());
        droid.setForceUser(dto.isForceUser());
        droid.setPrimaryFunction(dto.getPrimaryFunction());
        return droid;
    }

}
