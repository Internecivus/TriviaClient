package com.trivia.client.controller;

import com.trivia.client.model.Game;
import com.trivia.client.service.GameManager;
import com.trivia.client.utility.i18n;
import com.trivia.client.view.FXMLEnum;
import com.trivia.client.view.StageManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class HomeController {
    private final StageManager stageManager;
    private @FXML Pane mainPane;
    private @FXML StackPane stackPane;

    public HomeController() {
        stageManager = StageManager.getStageManager();
    }

    @FXML
    private void exitApplication(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, i18n.get("exit.confirmation.message"), ButtonType.NO, ButtonType.YES);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            Platform.exit();
        }
    }

    @FXML
    private void startGame(ActionEvent event) {
        Game game = GameManager.getGame();
        stageManager.switchScene(FXMLEnum.CATEGORIES);
    }
}
