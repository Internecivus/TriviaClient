package com.trivia.client.model;

import com.trivia.client.service.GameManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Game {
    public final static int ANSWER_TIMER_DURATION = 15; // seconds

    private List<Question> questions;
    private int currentQuestionPos;
    private Category category;
    private int score;
    private double time; // in seconds
    private GameDuration gameDuration;

    public List<String> getAllImagePaths() {
        return questions.stream().map(q -> q.getImage()).filter(g -> Objects.nonNull(g)).collect(Collectors.toList());
    }

    public GameDuration getGameDuration() {
        return gameDuration;
    }

    public void setGameDuration(GameDuration gameDuration) {
        this.gameDuration = gameDuration;
    }

    public int getCurrentQuestionPos() {
        return currentQuestionPos;
    }

    public void setCurrentQuestionPos(int currentQuestionPos) {
        this.currentQuestionPos = currentQuestionPos;
    }

    public Category getCategory() {
        return category;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }
}
