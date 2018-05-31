package com.trivia.client.service;

import com.trivia.client.model.Game;

public class GameManager {
    private static Game instance;

    private GameManager() {}

    public static synchronized void reset() {
        Game resetGame = new Game();
        resetGame.setGameDuration(instance.getGameDuration());
        instance = resetGame;
    }

    public static synchronized Game getGame() {
        if(instance == null) {
            instance = new Game();
        }
        return instance;
    }
}