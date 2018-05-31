package com.trivia.client.exception;

import com.trivia.client.utility.i18n;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Arrays;

public class Alerts {
    public static void showInternalError(Service... services) {
        Alert alert = new Alert(Alert.AlertType.ERROR, i18n.get("dialog.internalError.message"), ButtonType.OK, ButtonType.CLOSE);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            if (services.length > 0) Arrays.stream(services).forEach(s -> s.restart());
        }
        else if (alert.getResult() == ButtonType.CLOSE) {
            Platform.exit();
        }
    }
}
