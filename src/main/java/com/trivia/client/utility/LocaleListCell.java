package com.trivia.client.utility;

import javafx.scene.control.ListCell;

import java.util.Locale;

public class LocaleListCell extends ListCell<Locale> {
    @Override
    protected void updateItem(Locale locale, boolean empty) {
        super.updateItem(locale, empty) ;
        if (empty) {
            setText(null);
        }
        else {
            String language = locale.getDisplayLanguage(i18n.getCurrentLocale());
            setText(language.substring(0, 1).toUpperCase() + language.substring(1));
        }
    }
}