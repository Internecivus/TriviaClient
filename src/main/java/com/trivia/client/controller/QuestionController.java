package com.trivia.client.controller;

import com.trivia.client.model.Game;
import com.trivia.client.model.Question;
import com.trivia.client.service.GameManager;
import com.trivia.client.utility.FontSizeFinder;
import com.trivia.client.utility.ImageUtil;
import com.trivia.client.utility.StageManager;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.awt.event.WindowEvent;


public class QuestionController {
    private StageManager stageManager;
    private Game game;
    private Timeline timeline;
    private Question question;

    private @FXML Label questionLbl;
    private @FXML Button answerFirstBtn;
    private @FXML Button answerSecondBtn;
    private @FXML Button answerThirdBtn;
    private @FXML Button answerFourthBtn;
    private @FXML ProgressBar timerBar;
    private @FXML Button nextBtn;
    private @FXML AnchorPane mainPane;
    private @FXML StackPane headerPane;

    public QuestionController() {
        stageManager = StageManager.getStageManager();
        game = GameManager.getGame();
    }

    // Get and show the question.
    @FXML
    private void initialize() {
        setTimer();
        question = game.getQuestions().get(game.getCurrentQuestionPos());
        addQuestion();
    }

    private void setTimer() {
        timerBar.progressProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                double progress = newValue == null ? 0 : newValue.doubleValue();
                if (progress > 0.85) {
                    setBarStyleClass("progressBarWarn");
                }
                else if (progress > 0.6) {
                    setBarStyleClass("progressBarCaution");
                }
                else {
                    setBarStyleClass("progressBarFine");
                }
            }

            private void setBarStyleClass(String styleClass) {
                timerBar.getStyleClass().removeAll("progressBarCaution", "progressBarFine", "progressBarWarn");
                timerBar.getStyleClass().add(styleClass);
            }
        });

        timeline = new Timeline(
            new KeyFrame(Duration.ZERO, new KeyValue(timerBar.progressProperty(), 0)),
            new KeyFrame(Duration.seconds(Game.ANSWER_TIMER_DURATION), e -> {
                answer(0); // No answer selected.
            }, new KeyValue(timerBar.progressProperty(), 1))
        );
    }

    private void setBackgroundImage() {
        String imagePath = (question.getImageData() == null) ?
            game.getCategory().getImageData().getPath() : question.getImageData().getPath();
        BackgroundImage backgroundImage = new BackgroundImage(new Image("file:" + ImageUtil.IMAGE_DIR + "/" + imagePath),
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, true));

        mainPane.setBackground(new Background(backgroundImage));
    }

    // Show the question and start the timer.
    private void addQuestion() {
        questionLbl.setText(question.getQuestion());
        // I don't know of a better way to do this, since the real width gets calculated only after the scene is shown.
        // We could use delayed threads or in-memory replication to do this kinds of things, but that seems messy.
        // On the other hand, this is too...
        double questionLblWidth = stageManager.getPrimaryStage().getWidth()
            - ((stageManager.getPrimaryStage().getWidth() - questionLbl.getPrefWidth() > 0) ?
                stageManager.getPrimaryStage().getWidth() - questionLbl.getPrefWidth() : 0)
            - headerPane.getPadding().getLeft() - headerPane.getPadding().getRight()
            - questionLbl.getPadding().getLeft() - questionLbl.getPadding().getRight();

        questionLbl.setStyle(String.format("-fx-font-size: %dpx;", FontSizeFinder.findFor(
            questionLbl.getFont(), questionLbl.getText(), questionLblWidth, 2))
        );

        answerFirstBtn.setText(question.getAnswers().get(0));
        answerSecondBtn.setText(question.getAnswers().get(1));
        answerThirdBtn.setText(question.getAnswers().get(2));
        answerFourthBtn.setText(question.getAnswers().get(3));

        answerFirstBtn.setUserData(1);
        answerSecondBtn.setUserData(2);
        answerThirdBtn.setUserData(3);
        answerFourthBtn.setUserData(4);

        final BooleanProperty initialFocus = new SimpleBooleanProperty(true);
        answerFirstBtn.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
            if(newValue && initialFocus.get()){
                mainPane.requestFocus();
                initialFocus.setValue(false);
            }
        });

        setBackgroundImage();

        startTimer();
    }

    private void startTimer() {
        timerBar.setVisible(true);
        timeline.setCycleCount(1);
        timeline.play();
    }

    private void stopTimer() {
        game.setTime(game.getTime() + timeline.getCurrentTime().toSeconds());
        timeline.stop();
    }

    @FXML
    private void answer(ActionEvent event) {
        Node node = (Node) event.getSource();
        Integer userData = (Integer) node.getUserData();
        answer(userData);
    }

    public void disableAnswerBtns() {
        answerFirstBtn.setDisable(true);
        answerSecondBtn.setDisable(true);
        answerThirdBtn.setDisable(true);
        answerFourthBtn.setDisable(true);
    }

    private void answer(int answerNumber) {
        // Stop the timer and get the time.
        stopTimer();

        disableAnswerBtns();

        if (answerNumber == 0) {
            getAnswerBtn(question.getAnswerCorrect()).getStyleClass().add("success");

            // We could also just style them all as wrong, and style the right one after, but we might use this later
            // for transitions.
            for (int i = 1; i <= 4; i++) {
                if (i != question.getAnswerCorrect()) {
                    getAnswerBtn(i).getStyleClass().add("danger");
                }
            }
        }
        // Answer is correct.
        else if (question.isCorrect(answerNumber)) {
            game.setScore(game.getScore() + 1);

            getAnswerBtn(answerNumber).getStyleClass().add("success");
        }
        // Answer is wrong.
        else {
            getAnswerBtn(answerNumber).getStyleClass().add("danger");
            getAnswerBtn(question.getAnswerCorrect()).getStyleClass().add("success");
        }

        // Replace the Timer with the Next button.
        timerBar.setVisible(false);
        nextBtn.setVisible(true);
    }

    private Button getAnswerBtn(int i) {
        switch (i) {
            case 1:
                return answerFirstBtn;
            case 2:
                return answerSecondBtn;
            case 3:
                return answerThirdBtn;
            case 4:
                return answerFourthBtn;
            default:
                throw new IllegalArgumentException();
        }
    }

    // Go to the next question (start the whole process again).
    @FXML
    private void next() {
        QuestionsController.getInstance().next();
    }
}
