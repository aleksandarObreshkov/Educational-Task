package com.example.model.dto;

import com.example.model.Character;

import java.util.ArrayList;
import java.util.List;

public abstract class CharacterDTO extends Character {

    private List<Long> movieIds = new ArrayList<>();

    private List<Long> friendIds = new ArrayList<>();

    public List<Long> getMovieIds() {
        return movieIds;
    }

    public void setMovieIds(List<Long> movieIds) {
        this.movieIds = movieIds;
    }

    public List<Long> getFriendIds() {
        return friendIds;
    }

    public void setFriendIds(List<Long> friendIds) {
        this.friendIds = friendIds;
    }
}
