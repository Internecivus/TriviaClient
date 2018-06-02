package com.trivia.client.controller;

import com.trivia.client.model.Game;
import com.trivia.client.service.GameManager;
import com.trivia.client.utility.StageManager;
import com.trivia.client.view.FXMLEnum;

public class QuestionsController {
    private Game game;
    private StageManager stageManager;
    private static QuestionsController instance;

    public static synchronized QuestionsController getInstance() {
        return instance;
    }

    public QuestionsController() {}

    public void init() {
        instance = this;
        stageManager = StageManager.getStageManager();
        game = GameManager.getGame();

        start();
    }

    public void start() {
        stageManager.switchScene(FXMLEnum.QUESTION);
    }

    public void next() {
        int currentQuestionPos = game.getCurrentQuestionPos();
        // Set the position to the first/next question or the results page if we are done.
        if (currentQuestionPos < game.getQuestions().size() - 1) {
            game.setCurrentQuestionPos(++currentQuestionPos);
            stageManager.switchScene(FXMLEnum.QUESTION);
        }
        else {
            // Show final results.
            stageManager.switchScene(FXMLEnum.RESULTS);
        }
    }
}
