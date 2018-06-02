package com.trivia.client.utility;

import javafx.concurrent.Service;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.util.Arrays;

public class Alerts {
    public static void showInternalError(Service... services) {
        Alert alert = new Alert(Alert.AlertType.ERROR, i18n.get("dialog.internalError.message"), ButtonType.OK, ButtonType.CLOSE);

        Button okBtn = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        okBtn.setDefaultButton(true);

        Button closeBtn = (Button) alert.getDialogPane().lookupButton(ButtonType.CLOSE);
        closeBtn.setDefaultButton(false);

        alert.initOwner(StageManager.getStageManager().getPrimaryStage());
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            if (services.length > 0) Arrays.stream(services).forEach(s -> s.restart());
        }
        else if (alert.getResult() == ButtonType.CLOSE) {
            StageManager.saveAndExit();
        }
    }

    public static void showExit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, i18n.get("dialog.exit.message"), ButtonType.NO, ButtonType.YES);

        Button yesBtn = (Button) alert.getDialogPane().lookupButton(ButtonType.YES);
        yesBtn.setDefaultButton(false);

        Button noBtn = (Button) alert.getDialogPane().lookupButton( ButtonType.NO );
        noBtn.setDefaultButton(true);

        alert.initOwner(StageManager.getStageManager().getPrimaryStage());
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            StageManager.saveAndExit();
        }
    }
}
