package com.trivia.client.controller;

import com.trivia.client.model.Category;
import com.trivia.client.model.Game;
import com.trivia.client.model.Question;
import com.trivia.client.service.ClientService;
import com.trivia.client.service.GameManager;
import com.trivia.client.utility.i18n;
import com.trivia.client.view.FXMLEnum;
import com.trivia.client.view.StageManager;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.InputEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class QuestionController {
    // here we have a constructor with a category and fetch the questions
    // we then create a pane for each of them and present them one after another
    // we keep scores of how many have been correctly answered
    // each question has a time limit/counter
    private StageManager stageManager;
    private Game game;
    private Timeline timeline;

    private @FXML Label questionLbl;
    private @FXML Button answerFirstBtn;
    private @FXML Button answerSecondBtn;
    private @FXML Button answerThirdBtn;
    private @FXML Button answerFourthBtn;
    private @FXML ProgressBar timerBar;
    private @FXML GridPane bottomPane;
    private @FXML Button nextBtn;

    public QuestionController() {
        stageManager = StageManager.getStageManager();
        game = GameManager.getGame();
        Category category = game.getCategory();

        //get questions from category worker service
        List<Question> questions = new ArrayList<>();
        Question a = new Question(); a.setQuestion("Pitanje"); a.setAnswerCorrect(1); a.setAnswerFirst("Prvo"); a.setAnswerSecond("Drugo"); a.setAnswerFourth("Cetvrto"); a.setAnswerThird("Trece"); a.setImage("gdggds");
        Question b = new Question(); b.setQuestion("Pitanje"); b.setAnswerCorrect(1); b.setAnswerFirst("Prvo"); b.setAnswerSecond("Drugo"); b.setAnswerFourth("Cetvrto"); b.setAnswerThird("Trece"); b.setImage("gdggds");
        questions.add(a); questions.add(b);
        game.setQuestions(questions);
    }

    // Get and show the question.
    @FXML
    private void initialize() {
        answerFirstBtn.setUserData(1);
        answerSecondBtn.setUserData(2);
        answerThirdBtn.setUserData(3);
        answerFourthBtn.setUserData(4);

        setQuestion();
    }

    private void setQuestion() {
        Integer currentQuestionPos = game.getCurrentQuestionPos();

        // Set the position to the first/next question or the results page if we are done.
        if (currentQuestionPos == null) {
            game.setCurrentQuestionPos(0);
        }
        else if (currentQuestionPos < game.getQuestions().size() - 1) {
            game.setCurrentQuestionPos(++currentQuestionPos);
        }
        else {
            // Show final results.
            stageManager.switchScene(FXMLEnum.RESULTS);
        }

        Question question = game.getQuestions().get(game.getCurrentQuestionPos());
        addQuestion(question);
    }

    // Show the question and start the timer.
    private void addQuestion(Question question) {
        questionLbl.setText(question.getQuestion());

        answerFirstBtn.setText(question.getAnswerFirst());
        answerSecondBtn.setText(question.getAnswerSecond());
        answerThirdBtn.setText(question.getAnswerThird());
        answerFourthBtn.setText(question.getAnswerFourth());

        startTimer();
    }

    // TODO: Show seconds next to the timerBar.
    private void startTimer() {
        timerBar.setVisible(true);

        timerBar.progressProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                double progress = newValue == null ? 0 : newValue.doubleValue();
                if (progress > 0.85) {
                    setBarStyleClass(timerBar, "-fx-accent: red");
                } else if (progress > 0.7) {
                    setBarStyleClass(timerBar, "-fx-accent: orange");
                } else {
                    setBarStyleClass(timerBar, "-fx-accent: blue");
                }
            }

            private void setBarStyleClass(ProgressBar bar, String barStyleClass) {
                timerBar.getStyleClass().removeAll("-fx-accent: red", "-fx-accent: orange", "-fx-accent: blue");
                timerBar.getStyleClass().add(barStyleClass);
            }
        });

        timeline = new Timeline(
            new KeyFrame(Duration.ZERO, new KeyValue(timerBar.progressProperty(), 0)),
            new KeyFrame(Duration.seconds(Game.ANSWER_TIMER_DURATION), e -> {
                answer(0); // No answer selected.
            }, new KeyValue(timerBar.progressProperty(), 1))
        );

        timeline.setCycleCount(1);
        timeline.play();
    }

    private void stopTimer() {
        timeline.stop();
    }

    @FXML
    private void answer(ActionEvent event) {
        Node node = (Node) event.getSource() ;
        Integer userData = (Integer) node.getUserData();
        answer(userData);
    }

    private void answer(int answerNumber) {
        // Stop the timer and get the time.
        game.setTime(game.getTime() + timeline.getCurrentTime().toSeconds());
        stopTimer();


        // Answer is correct.
        if (game.getQuestions().get(game.getCurrentQuestionPos()).isCorrect(answerNumber)) {
            game.setScore(game.getScore() + 1);


        }
        // Answer is wrong.
        else {
            // Circle the incorrect answer.
        }

        // Circle the correct answer.

        // Show the results in any case.
        showResult();


        // TODO: Show the time needed to answer.

        // replace the Timer with the Next button
        timerBar.setVisible(false);
        nextBtn.setVisible(true);
    }

    private void showResult() {

    }

    // Go to the next question (start the whole process again).
    @FXML
    private void next() {
        nextBtn.setVisible(false);
        setQuestion();
    }
}
