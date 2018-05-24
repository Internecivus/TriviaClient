package com.trivia.client.service;

import com.trivia.client.model.Game;
import com.trivia.client.view.StageManager;
import javafx.stage.Stage;

public class GameManager {
    private static Game instance;

    private GameManager(){}

    public static synchronized Game getGame(){
        if(instance == null){
            instance = new Game();
        }
        return instance;
    }
}