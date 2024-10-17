package com.example.springredditclone.model;

import java.util.Arrays;
import java.util.Optional;

public enum VoteType {
    UPVOTE(1), 
    DOWNVOTE(-1);

    private int direction;

    VoteType(int direction) {
        this.direction = direction;
    }

    public static Optional<VoteType> lookup(Integer direction) {
        return Arrays.stream(VoteType.values())
                .filter(value -> value.getDirection().equals(direction))
                .findFirst();
    }

    public Integer getDirection() {
        return direction;
    }
}
