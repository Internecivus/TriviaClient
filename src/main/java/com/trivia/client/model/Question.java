package com.trivia.client.model;



public class Question {
    private Integer id;
    private String question;
    private String answerFirst;
    private String answerSecond;
    private String answerThird;
    private String answerFourth;
    private int answerCorrect;
    private String image;

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

    public String getAnswerFirst() {
        return answerFirst;
    }

    public void setAnswerFirst(String answerFirst) {
        this.answerFirst = answerFirst;
    }

    public String getAnswerSecond() {
        return answerSecond;
    }

    public void setAnswerSecond(String answerSecond) {
        this.answerSecond = answerSecond;
    }

    public String getAnswerThird() {
        return answerThird;
    }

    public void setAnswerThird(String answerThird) {
        this.answerThird = answerThird;
    }

    public String getAnswerFourth() {
        return answerFourth;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
