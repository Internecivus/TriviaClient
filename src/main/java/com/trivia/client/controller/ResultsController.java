package com.trivia.client.controller;

import com.trivia.client.model.Game;
import com.trivia.client.service.GameManager;
import com.trivia.client.view.StageManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


// TODO: Present the score and time
// TODO: Give the option to play a new game
// TOOO: Social media sharing
public class ResultsController {
    private StageManager stageManager;
    private Game game;

    private @FXML Label scoreLbl;
    private @FXML Label timeLbl;

    public ResultsController() {
        stageManager = StageManager.getStageManager();
        game = GameManager.getGame();
    }

    @FXML
    private void initialize() {
        scoreLbl.setText(String.valueOf(game.getScore()));
        timeLbl.setText(String.format("%.2f", game.getTime()));
    }

}