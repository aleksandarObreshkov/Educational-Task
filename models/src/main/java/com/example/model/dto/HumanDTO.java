package com.example.model.dto;

import com.example.processor.Validate;

import java.util.ArrayList;
import java.util.List;
@Validate
public class HumanDTO extends CharacterDTO{

    public String getCharacterType() {
        return "human";
    }

    private List<Long> starshipsIds = new ArrayList<>();

    public List<Long> getStarshipsIds() {
        return starshipsIds;
    }

    public void setStarshipsIds(List<Long> starshipsIds) {
        this.starshipsIds = starshipsIds;
    }
}
