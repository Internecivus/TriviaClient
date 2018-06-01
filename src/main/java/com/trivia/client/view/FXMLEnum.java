package com.trivia.client.view;

import com.trivia.client.utility.i18n;

public enum FXMLEnum {
    HOME {
        @Override
        public String getFxmlFile() {
            return "/fxml/Home.fxml";
        }

        @Override
        public String getTitle() {
            return i18n.get("home.title");
        }
    },

    CATEGORIES {
        @Override
        public String getFxmlFile() {
            return "/fxml/Categories.fxml";
        }

        @Override
        public String getTitle() {
            return i18n.get("categories.title");
        }
    },

    QUESTION {
        @Override
        public String getFxmlFile() {
            return "/fxml/Question.fxml";
        }

        @Override
        public String getTitle() {
            return i18n.get("question.title");
        }
    },

    RESULTS {
        @Override
        public String getFxmlFile() {
            return "/fxml/Results.fxml";
        }

        @Override
        public String getTitle() {
            return i18n.get("results.title");
        }
    };

    public abstract String getFxmlFile();
    public abstract String getTitle();
}
