package com.trivia.client.model;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Question {
    private Integer id;
    private String question;
    private String answerFirst;
    private String answerSecond;
    private String answerThird;
    private String answerFourth;
    private int answerCorrect;
    private ImageData imageData;
    private List<String> answers;

    public void randomizeAnswers() {
        answers = new ArrayList<>();
        answers.add(answerFirst);
        answers.add(answerSecond);
        answers.add(answerThird);
        answers.add(answerFourth);

        String correct = answers.get(answerCorrect - 1);
        Collections.shuffle(answers);
        answerCorrect = answers.indexOf(correct) + 1;
    }

    public ImageData getImageData() {
        return imageData;
    }

    public void setImageData(ImageData imageData) {
        this.imageData = imageData;
    }

    public boolean isCorrect(int answerNo) {
        return (answerNo == answerCorrect);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswerFirst(String answerFirst) {
        this.answerFirst = answerFirst;
    }

    public void setAnswerSecond(String answerSecond) {
        this.answerSecond = answerSecond;
    }

    public void setAnswerThird(String answerThird) {
        this.answerThird = answerThird;
    }

    public void setAnswerFourth(String answerFourth) {
        this.answerFourth = answerFourth;
    }

    public int getAnswerCorrect() {
        return answerCorrect;
    }

    public void setAnswerCorrect(int answerCorrect) {
        this.answerCorrect = answerCorrect;
    }

    public List<String> getAnswers() {
        return answers;
    }
}
