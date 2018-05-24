package com.trivia.client.utility;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.Locale;
import java.util.ResourceBundle;

public class i18n {
    private i18n() {}

    public static String get(String key) {
        return ResourceBundle.getBundle("i18n").getString(key);
    }
}
