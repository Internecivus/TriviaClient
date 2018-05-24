package com.trivia.client.view;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public final class StageManager {
    private static StageManager instance;

    private StageManager(){}

    public static synchronized StageManager getStageManager(){
        if(instance == null){
            instance = new StageManager();
        }
        return instance;
    }

    private Stage primaryStage;

    public void init(Stage stage) {
        this.primaryStage = stage;
    }

    public void switchScene(final FXMLEnum view) {
        Parent viewRootNodeHierarchy = loadViewNodeHierarchy(view.getFxmlFile());
        show(viewRootNodeHierarchy, view.getTitle());
    }

    private void show(final Parent rootnode, String title) {
        Scene scene = prepareScene(rootnode);

        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();

        try {
            primaryStage.show();
        } catch (Exception exception) {
            logAndExit ("Unable to show scene for title" + title,  exception);
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

    /**
     * Loads the object hierarchy from a FXMLEnum document and returns to root node
     * of that hierarchy.
     *
     * @return Parent root node of the FXMLEnum document hierarchy
     */
    private Parent loadViewNodeHierarchy(String fxmlFilePath) {
        Parent rootNode = null;
        try {
            rootNode = FXMLLoader.load(getClass().getResource(fxmlFilePath));
            Objects.requireNonNull(rootNode, "A Root FXMLEnum node must not be null");
        } catch (Exception exception) {
            logAndExit("Unable to load FXMLEnum view" + fxmlFilePath, exception);
        }
        return rootNode;
    }


    private void logAndExit(String errorMsg, Exception exception) {
        Platform.exit();
    }
}