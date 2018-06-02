package com.trivia.client;

import com.trivia.client.utility.StageManager;
import com.trivia.client.view.FXMLEnum;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    protected StageManager stageManager = null;

    @Override
    public void start(Stage stage) {
        stage.getIcons().add(new Image("icon.png"));
        StageManager.getStageManager().init(stage);
        stageManager = StageManager.getStageManager();
        stageManager.switchScene(FXMLEnum.HOME);
    }

    public static void main(String[] args) {
        launch(args);
    }
}