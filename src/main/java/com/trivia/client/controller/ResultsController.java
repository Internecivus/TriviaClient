package com.trivia.client.controller;

import com.trivia.client.model.Game;
import com.trivia.client.service.GameManager;
import com.trivia.client.utility.Alerts;
import com.trivia.client.utility.StageManager;
import com.trivia.client.view.FXMLEnum;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;



public class ResultsController {
    private StageManager stageManager;
    private Game game;

    private @FXML AnchorPane mainPane;
    private @FXML Label scoreLbl;
    private @FXML Label timeLbl;

    public ResultsController() {
        stageManager = StageManager.getStageManager();
        game = GameManager.getGame();
    }

    @FXML
    private void initialize() {
        scoreLbl.setText(String.format("%d/%d", game.getScore(), game.getGameDuration().getSize()));
        scoreLbl.setFont(resizeScore(scoreLbl.getFont()));
        timeLbl.setText(String.format("%.2f/%ds", game.getTime(), game.getGameDuration().getSize() * Game.ANSWER_TIMER_DURATION));

        setImage();
    }

    private void setImage() {
        BackgroundImage backgroundImage = new BackgroundImage(new Image("/images/results.jpg"),
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, true));

        mainPane.setBackground(new Background(backgroundImage));
    }

    @FXML
    private Font resizeScore(Font oldFont) {
        double scorePercentage = (double) game.getScore() / game.getGameDuration().getSize();
        Font newFont = new Font(oldFont.getName(), oldFont.getSize() + oldFont.getSize() * scorePercentage);
        return newFont;
    }

    @FXML
    private void restart() {
        GameManager.reset();
        stageManager.switchScene(FXMLEnum.CATEGORIES);
    }

    @FXML
    private void exit() {
        Alerts.showExit();
    }

}