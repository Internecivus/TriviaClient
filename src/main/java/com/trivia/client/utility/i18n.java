package com.trivia.client.utility;

import java.util.*;
import java.util.prefs.Preferences;

public class i18n {
    private static String DEFAULT_LANGUAGE = "en";
    private static List<Locale> availableLocales = Arrays.asList(new Locale("en"), new Locale("hr"));

    private static Locale currentLocale;
    private static Preferences localePreferences = Preferences.userRoot().node("Trivia").node("locale");

    private i18n() {}

    public static void init() {
        currentLocale = availableLocales
            .stream()
            .filter(l -> l.getLanguage().equals(localePreferences.get("language", DEFAULT_LANGUAGE)))
            .findFirst()
            .orElse(new Locale(DEFAULT_LANGUAGE));
    }

    public static void save() {
        localePreferences.put("language", currentLocale.getLanguage());
    }

    public static ResourceBundle getBundle() {
        return PropertyResourceBundle.getBundle("i18n", currentLocale);
    }

    public static String get(String key, Object... parameters) {
        ResourceBundle bundle = PropertyResourceBundle.getBundle("i18n", currentLocale);
        String message = key;
        if (bundle.containsKey(key)) {
            message = bundle.getString(message);
        }
        return (parameters.length == 0) ? message : String.format(message, parameters);
    }

    public static Locale getCurrentLocale() {
        return currentLocale;
    }

    public static void setCurrentLocale(Locale currentLocale) {
        i18n.currentLocale = currentLocale;
    }

    public static List<Locale> getAvailableLocales() {
        return availableLocales;
    }
}