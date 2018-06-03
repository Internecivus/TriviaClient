package com.trivia.client.controller;

import com.trivia.client.model.GameDuration;
import com.trivia.client.service.GameManager;
import com.trivia.client.utility.Alerts;
import com.trivia.client.utility.LocaleListCell;
import com.trivia.client.utility.StageManager;
import com.trivia.client.utility.i18n;
import com.trivia.client.view.FXMLEnum;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.StageStyle;

import java.util.Locale;

public class HomeController {
    private final StageManager stageManager;

    private @FXML Button newGameBtn;
    private @FXML Button exitBtn;
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
                stageManager.switchScene(FXMLEnum.HOME);
            }
        });
    }

    @FXML
    private void exitApplication(ActionEvent event) {
        Alerts.showExit();
    }

    @FXML
    private void startGame(ActionEvent event) {
        GameDuration gameDuration = gameDurationDialog();
        if (gameDuration != null) {
            GameManager.getGame().setGameDuration(gameDuration);
            stageManager.switchScene(FXMLEnum.CATEGORIES);
        }
    }

    public GameDuration gameDurationDialog() {
        GameDuration gameDuration;
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setContentText(i18n.get("dialog.duration.message"));

        dialog.initStyle(StageStyle.UTILITY);

        Label label = new Label(i18n.get("dialog.duration.message"));
        label.getStyleClass().add("dialogContent");
        label.setAlignment(Pos.CENTER);
        dialog.getDialogPane().setContent(label);

        ButtonType gameShortBtnType = new ButtonType(i18n.get("duration.short"));
        ButtonType gameMediumBtnType = new ButtonType(i18n.get("duration.medium"));
        ButtonType gameLongBtnType = new ButtonType(i18n.get("duration.long"));
        ButtonType cancelBtnType = new ButtonType(i18n.get("cancel"), ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().setAll(gameShortBtnType, gameMediumBtnType, gameLongBtnType, cancelBtnType);
        dialog.getDialogPane().getStyleClass().add("dialogContent");

        Button cancelBtn = (Button) dialog.getDialogPane().lookupButton(cancelBtnType);
        cancelBtn.managedProperty().bind(cancelBtn.visibleProperty());
        cancelBtn.setVisible(false);

        dialog.initOwner(stageManager.getPrimaryStage());
        dialog.showAndWait();
        if (dialog.getResult() == (gameShortBtnType)) {
            gameDuration = GameDuration.SHORT;
        }
        else if (dialog.getResult() == (gameMediumBtnType)) {
            gameDuration = GameDuration.MEDIUM;
        }
        else if (dialog.getResult() == (gameLongBtnType)) {
            gameDuration = GameDuration.LONG;
        }
        else {
            gameDuration = null;
        }
        return gameDuration;
    }
}
