package com.trivia.client.utility;

import com.trivia.client.service.ClientManager;
import com.trivia.client.view.FXMLEnum;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Objects;
import java.util.ResourceBundle;

/**
 * The idea partly taken from https://github.com/mvpjava/javafx-switching-scenes-part2-tutorial
 */
public final class StageManager {
    private static StageManager instance;

    private StageManager() {}

    public static synchronized StageManager getStageManager() {
        if (instance == null) {
            instance = new StageManager();
        }
        return instance;
    }

    private Stage primaryStage;

    public void init(Stage stage) {
        this.primaryStage = stage;
        ClientManager.init();
        TempUtil.init();
        StageSettings.init(stage);
        i18n.init();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                saveAndExit();
            }
        });
    }

    public void switchScene(FXMLEnum view) {
        Parent viewRootNodeHierarchy = loadViewNodeHierarchy(view.getFxmlFile());
        show(viewRootNodeHierarchy, view.getTitle());
    }

    private void show(final Parent rootNode, String title) {
        Scene scene = prepareScene(rootNode);

        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        StageSettings.apply();

        try {
            primaryStage.show();
        }
        catch (Exception exception) {
            Platform.exit();
        }
    }

    private Scene prepareScene(Parent rootnode){
        Scene scene = primaryStage.getScene();

        if (scene == null) {
            scene = new Scene(rootnode);
        }
        scene.setRoot(rootnode);
        return scene;
    }

    private Parent loadViewNodeHierarchy(String fxmlFilePath) {
        Parent rootNode = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFilePath));
            loader.setResources(i18n.getBundle());
            loader.load();
            rootNode = loader.getRoot();

            //rootNode = FXMLLoader.load(getClass().getResource(fxmlFilePath));
            Objects.requireNonNull(rootNode, "A Root FXMLEnum node must not be null!");
        }
        catch (Exception exception) {
            saveAndExit();
        }
        return rootNode;
    }

    public static void saveAndExit() {
        StageSettings.save();
        i18n.save();
        Platform.exit();
        System.exit(0);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}