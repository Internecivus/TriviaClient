package com.trivia.client.utility;

import javafx.stage.Stage;

import java.util.prefs.Preferences;

public class StageSettings {
    private static int DEFAULT_WIDTH = 800;
    private static int DEFAULT_HEIGHT = 576;
    private static boolean DEFAULT_MAXIMIZED = false;
    private static int MIN_WIDTH = 640;
    private static int MIN_HEIGHT = 520;

    private static boolean maximized;
    private static double x;
    private static double y;
    private static double width;
    private static double height;

    private static Preferences windowPreferences = Preferences.userRoot().node("Trivia").node("window");
    private static Stage currentStage;

    public static void init(Stage stage) {
        currentStage = stage;

        x = windowPreferences.getDouble("x", -1);
        y = windowPreferences.getDouble("y", -1);
        width = windowPreferences.getDouble("width", DEFAULT_WIDTH);
        height = windowPreferences.getDouble("height", DEFAULT_HEIGHT);
        maximized = windowPreferences.getBoolean("maximized", DEFAULT_MAXIMIZED);

        currentStage.setMinWidth(MIN_WIDTH);
        currentStage.setMinHeight(MIN_HEIGHT);

        initListener();
    }

    public static void apply() {
        setSize();
        setPosition();
    }

    private static void setSize() {
        currentStage.setWidth(width);
        currentStage.setHeight(height);
        currentStage.setMaximized(maximized);
    }

    private static void setPosition() {
        if (x > 0 && y > 0) {
            currentStage.setX(x);
            currentStage.setY(y);
        }
        else {
            currentStage.centerOnScreen();
        }
    }

    public static void save() {
        windowPreferences.putDouble("x", x);
        windowPreferences.putDouble("y", y);
        windowPreferences.putDouble("width", width);
        windowPreferences.putDouble("height", height);
        windowPreferences.putBoolean("maximized", maximized);
    }

    private static void initListener() {
        currentStage.xProperty().addListener((observable, oldX, newX) -> { x = (double) newX; });
        currentStage.yProperty().addListener((observable, oldY, newY) -> { y = (double) newY; });
        currentStage.widthProperty().addListener((observable, oldWidth, newWidth) -> { width = (double) newWidth; });
        currentStage.heightProperty().addListener((observable, oldHeight, newHeight) -> { height = (double) newHeight; });
        currentStage.maximizedProperty().addListener((observable, oldMaximized, newMaximized) -> { maximized = newMaximized; });
    }
}
