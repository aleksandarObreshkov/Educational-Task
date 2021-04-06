package com.example.model.dto;

import com.example.processor.Validate;

@Validate
public class DroidDTO extends CharacterDTO{

    private String primaryFunction;

    public String getPrimaryFunction() {
        return primaryFunction;
    }

    public void setPrimaryFunction(String primaryFunction) {
        this.primaryFunction = primaryFunction;
    }

    @Override
    public String getCharacterType() {
        return "droid";
    }
}
