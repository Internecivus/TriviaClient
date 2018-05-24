package com.trivia.client.model;

import java.util.Date;
import java.util.List;

public class Game {
    //TODO: maybe have a short, medium, long game options? (10, 20, 50 questions)
    public final static int QUESTION_RANDOM_SIZE_SHORT = 10;
    public final static int QUESTION_RANDOM_SIZE_MEDIUM = 20;
    public final static int QUESTION_RANDOM_SIZE_LONG = 50;
    public final static int ANSWER_TIMER_DURATION = 20; // seconds

    private List<Question> questions;
    private Integer currentQuestionPos;
    private Category category;
    private int score;
    private double time; // in seconds

    public Integer getCurrentQuestionPos() {
        return currentQuestionPos;
    }

    public void setCurrentQuestionPos(Integer currentQuestionPos) {
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
