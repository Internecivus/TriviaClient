package com.trivia.client;

import com.trivia.client.view.FXMLEnum;
import com.trivia.client.view.StageManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class Main extends Application {
    protected StageManager stageManager = null;

    @Override
    public void start(Stage stage) throws Exception{
        StageManager.getStageManager().init(stage);
        stageManager = StageManager.getStageManager();
        stageManager.switchScene(FXMLEnum.HOME);
    }

    public static void main(String[] args) {
        launch(args);
    }
}