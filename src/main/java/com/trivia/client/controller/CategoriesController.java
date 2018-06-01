package com.trivia.client.controller;

import com.trivia.client.utility.Alerts;
import com.trivia.client.model.Category;
import com.trivia.client.model.Game;
import com.trivia.client.service.CategoriesService;
import com.trivia.client.service.GameManager;
import com.trivia.client.service.QuestionsService;
import com.trivia.client.utility.ImageUtil;
import com.trivia.client.utility.StageManager;
import com.trivia.client.view.FXMLEnum;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.util.List;



public class CategoriesController {
    private final int ROW_SIZE = 4;

    private final StageManager stageManager;
    private final Game game;
    private List<Category> categories;

    private @FXML AnchorPane mainPane;
    private @FXML GridPane categoriesPane;
    private @FXML ProgressIndicator progressIndicator;

    public CategoriesController() {
        stageManager = StageManager.getStageManager();
        game = GameManager.getGame();
    }

    @FXML
    private void initialize() {
        progressIndicator.setVisible(false);
        setBackgroundImage();
        setCategories();
    }

    private void setBackgroundImage() {
        BackgroundImage backgroundImage = new BackgroundImage(new Image("/images/categories.jpg"),
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, true));

        mainPane.setBackground(new Background(backgroundImage));
    }

    private void setCategories() {
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
        categories.addAll(categories);
        categories.addAll(categories);

        for (int i = 0; i < categories.size(); i++) {
            categoriesPane.add(getCategoryBox(categories.get(i)), i % ROW_SIZE, i / ROW_SIZE);
        }
    }

    private VBox getCategoryBox(Category category) {
        VBox categoryBox = new VBox();
        categoryBox.getStyleClass().add("listItem");
        categoryBox.setFillWidth(true);
        categoryBox.setAlignment(Pos.BOTTOM_CENTER);
        //TODO SIZE NOT PROGRAMMATIC
        categoryBox.setPrefHeight(100);

        // Set image.
        categoryBox.setBackground(getCategoryImage(category));

        // Set text label.
        Label label = new Label(category.getName());
        label.getStyleClass().add("listLabel");
        categoryBox.getChildren().add(label);

        categoryBox.setOnMouseClicked((e) -> {
            categoryBox.requestFocus();
            selectCategory(category.getId());
        });

        return categoryBox;
    }

    private Background getCategoryImage(Category category) {
        // TODO size not programmatic
        BackgroundImage backgroundImage = new BackgroundImage(
            new Image("file:" + ImageUtil.IMAGE_DIR + "/" + category.getImageData().getPath(), 600, 0, true, true, true),
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(100, 100, true, true, true, true));
        return new Background(backgroundImage);
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

    @FXML
    public void backToHome(ActionEvent event) {
        stageManager.switchScene(FXMLEnum.HOME);
    }
}