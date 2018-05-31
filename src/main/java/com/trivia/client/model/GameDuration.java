package com.trivia.client.model;

public enum GameDuration {
    SHORT(5),
    MEDIUM(10),
    LONG(15);

    int size;

    public int getSize() {
        return size;
    }

    GameDuration(int size) {
        this.size = size;
    }
}