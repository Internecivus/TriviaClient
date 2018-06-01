package com.trivia.client.controller;

import com.trivia.client.utility.Alerts;
import com.trivia.client.model.GameDuration;
import com.trivia.client.service.ClientManager;
import com.trivia.client.service.GameManager;
import com.trivia.client.utility.LocaleListCell;
import com.trivia.client.utility.i18n;
import com.trivia.client.view.FXMLEnum;
import com.trivia.client.utility.StageManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.StageStyle;

import java.util.Locale;

public class HomeController {
    private final StageManager stageManager;

    private @FXML AnchorPane mainPane;
    private @FXML StackPane stackPane;
    private @FXML ComboBox<Locale> languageBox;

    public HomeController() {
        stageManager = StageManager.getStageManager();
    }

    @FXML
    public void initialize() {
        initLanguageBox();
        setImage();
    }

    private void setImage() {
        BackgroundImage backgroundImage = new BackgroundImage(new Image("/images/categories.jpg"),
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, true));

        mainPane.setBackground(new Background(backgroundImage));
    }

    public void initLanguageBox() {
        languageBox.setCellFactory(l -> new LocaleListCell());
        languageBox.setButtonCell(new LocaleListCell());
        languageBox.getItems().addAll(i18n.getAvailableLocales());

        languageBox.getSelectionModel().select(i18n.getCurrentLocale());
        languageBox.valueProperty().addListener(new ChangeListener<Locale>() {
            @Override
            public void changed(ObservableValue observable, Locale oldLocale, Locale newLocale) {
                i18n.setCurrentLocale(newLocale);
            }
        });
    }

    @FXML
    private void exitApplication(ActionEvent event) {
        Alerts.showExit();
    }

    @FXML
    private void startGame(ActionEvent event) {
        try {
            GameDuration gameDuration = gameDurationDialog();
            if (gameDuration != null) {
                GameManager.getGame().setGameDuration(gameDuration);
                ClientManager.init();
                stageManager.switchScene(FXMLEnum.CATEGORIES);
            }
        }
        catch (Exception e) {
            // TODO: handle error with dialog and retry
            throw e;
        }
    }

    // TODO: Styles
    public GameDuration gameDurationDialog() {
        GameDuration gameDuration;
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setContentText(i18n.get("dialog.duration.message"));

        dialog.initStyle(StageStyle.UTILITY);

        Label label = new Label(i18n.get("dialog.duration.message"));
        label.getStyleClass().add("dialogContent");
        dialog.getDialogPane().setContent(label);

        ButtonType gameShortBtn = new ButtonType(i18n.get("duration.short"));

        ButtonType gameMediumBtn = new ButtonType(i18n.get("duration.medium"));
        ButtonType gameLongBtn = new ButtonType(i18n.get("duration.long"));
        ButtonType cancelBtn = new ButtonType(i18n.get("cancel"), ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().setAll(gameShortBtn, gameMediumBtn, gameLongBtn, cancelBtn);
        dialog.getDialogPane().getStyleClass().add("dialogContent");

        dialog.initOwner(stageManager.getPrimaryStage());
        dialog.showAndWait();
        if (dialog.getResult() == (gameShortBtn)) {
            gameDuration = GameDuration.SHORT;
        }
        else if (dialog.getResult() == (gameMediumBtn)) {
            gameDuration = GameDuration.MEDIUM;
        }
        else if (dialog.getResult() == (gameLongBtn)) {
            gameDuration = GameDuration.LONG;
        }
        else {
            gameDuration = null;
        }
        return gameDuration;
    }
}
