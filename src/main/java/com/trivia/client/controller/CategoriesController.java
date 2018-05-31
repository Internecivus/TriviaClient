package com.trivia.client.controller;

import com.trivia.client.exception.Alerts;
import com.trivia.client.model.Category;
import com.trivia.client.model.Game;
import com.trivia.client.service.CategoriesService;
import com.trivia.client.service.GameManager;
import com.trivia.client.service.QuestionsService;
import com.trivia.client.utility.ImageUtils;
import com.trivia.client.utility.StageManager;
import com.trivia.client.utility.i18n;
import javafx.application.Platform;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.List;



public class CategoriesController {
    private final StageManager stageManager;
    private final Game game;
    private List<Category> categories;

    private @FXML AnchorPane mainPane;
    private @FXML TilePane categoriesPane;
    private @FXML ProgressIndicator progressIndicator;

    public CategoriesController() {
        stageManager = StageManager.getStageManager();
        game = GameManager.getGame();
    }

    @FXML
    private void initialize() {
        progressIndicator.setVisible(false);
        setImage();
        getCategories();
    }

    private void setImage() {
        BackgroundImage backgroundImage = new BackgroundImage(new Image("/images/categories.jpg"),
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, true));

        mainPane.setBackground(new Background(backgroundImage));
    }

    public void getCategories() {
        CategoriesService categoriesService = new CategoriesService();
        progressIndicator.visibleProperty().bind(categoriesService.runningProperty());
        categoriesService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                categories = categoriesService.getValue();
                addCategories(categories);
            }
        });
        categoriesService.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                Alerts.showInternalError(categoriesService);
            }
        });

        categoriesService.start();
    }

    private void addCategories(List<Category> categories) {
        categoriesPane.setStyle("-fx-background-color: rgba(255, 215, 0, 0.1);");
        categoriesPane.setMaxWidth(Region.USE_PREF_SIZE);
        for (Category category : categories) {
            addCategory(category);
        }
    }

    private void addCategory(Category category) {
        // TODO: Category pane.
        VBox categoryBox = new VBox();
        categoryBox.getStyleClass().add("tile");
        categoryBox.setFillWidth(true);

        // Set image.
        setCategoryImage(category, categoryBox);

        // Set text label.
        Label label = new Label(category.getName());
        label.getStyleClass().add("boxLabel");
        categoryBox.getChildren().add(label);


        categoryBox.setOnMouseClicked((e) -> {
            categoryBox.requestFocus();
            selectCategory(category.getId());
        });

        categoriesPane.getChildren().add(categoryBox);
    }

    private void setCategoryImage(Category category, VBox categoryBox) {
        BackgroundImage backgroundImage = new BackgroundImage(
            new Image("file:" + ImageUtils.IMAGE_DIR + "/" + category.getImage()),
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(100, 100, true, true, true, true));
        categoryBox.setBackground(new Background(backgroundImage));
    }

    private void selectCategory(int id) {
        Category category = categories.stream().filter(cat -> cat.getId().equals(id)).findFirst().get();
        game.setCategory(category);
        getQuestions();
    }

    private void getQuestions() {
        QuestionsService questionsService = new QuestionsService();
        progressIndicator.visibleProperty().bind(questionsService.runningProperty());
        questionsService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                QuestionsController questionsController = new QuestionsController();
                questionsController.init();
            }
        });
        questionsService.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                Alerts.showInternalError(questionsService);
            }
        });
        questionsService.restart();
    }
}