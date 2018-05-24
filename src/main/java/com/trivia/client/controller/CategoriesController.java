package com.trivia.client.controller;

import com.trivia.client.model.Category;
import com.trivia.client.model.Game;
import com.trivia.client.service.ClientService;
import com.trivia.client.service.GameManager;
import com.trivia.client.view.FXMLEnum;
import com.trivia.client.view.StageManager;
import javafx.beans.binding.Bindings;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Control;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * Created by faust. Part of TriviaClient Project. All rights reserved. 2018
 */
public class CategoriesController {
    private final StageManager stageManager;
    private final Game game;
    private @FXML StackPane stackPane;
    private @FXML GridPane categoriesPane;
    private @FXML ProgressIndicator progressIndicator;
    private List<Category> categories;

    public CategoriesController() {
        stageManager = StageManager.getStageManager();
        game = GameManager.getGame();
    }

    @FXML
    private void initialize() {
//        VBox progressBox = new VBox(progressIndicator);
//        progressBox.setAlignment(Pos.CENTER);
//        stackPane.setDisable(true);
//        stackPane.getChildren().add(progressBox);

        final ClientService categoryService = new ClientService();
        progressIndicator.visibleProperty().bind(categoryService.runningProperty());
        categoryService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                categories = categoryService.getValue();

                if (categories == null || categories.size() < 1) {
                    categoryService.restart();
                }

                addCategories(categories);
            }
        });
        categoryService.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                //DO stuff on failed
            }
        });
        categoryService.restart();


        Category c = new Category(); c.setId(1); c.setName("ggg"); c.setImage("/Users/faust/Downloads/gg.jpg"); c.setDescription("Opis");
        Category b = new Category(); b.setId(2); b.setName("ggg"); b.setImage("/Users/faust/Downloads/gg.jpg"); b.setDescription("Opis");
        categories = Arrays.asList(c, b);

        addCategories(categories);
    }

    private void addCategories(List<Category> categories) {
//        categoriesPane.setPadding(new Insets(5, 0, 5, 0));
//        categoriesPane.setVgap(4);
//        categoriesPane.setHgap(4);
//        categoriesPane.setStyle("-fx-background-color: DAE6F3;");

        for (Category category : categories) {
            addCategory(category);
        }
    }

    private void addCategory(Category category) {
        try {

            // Category pane.
            VBox categoryBox = new VBox();
            //GridPane.setHgrow(categoryBox, Priority.ALWAYS);

            // Get image.
            //URL url = new URL("localhost");
            //BufferedImage c = ImageIO.read(url);
            Image image = new Image(new FileInputStream(category.getImage()));
            ImageView categoryImage = new ImageView(image);
            categoryImage.fitWidthProperty().bind(categoriesPane.widthProperty());
            //categoryImage.fitWidthProperty().bind(categoryBox.widthProperty());

            categoryBox.getChildren().add(categoryImage);

            categoriesPane.getChildren().add(categoryBox);

            categoryBox.setOnMouseClicked((e) -> {
                categoryBox.requestFocus();
                selectCategory(category.getId());
            });

//            categoryBox.backgroundProperty().bind(Bindings
//                .when( vb.focusedProperty() )
//                .then( focusBackground )
//                .otherwise( unfocusBackground )
//            );
        }
        catch (IOException e) {

        }
    }

    private void selectCategory(Integer id) {
        Category category = categories.stream().filter(cat -> cat.getId().equals(id)).findFirst().get();
        game.setCategory(category);
        stageManager.switchScene(FXMLEnum.QUESTION);
    }
}
