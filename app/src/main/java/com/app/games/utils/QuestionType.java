package com.app.games.utils;

public class QuestionType {
    private String question;
    private String imageURL;

    public QuestionType() {
    }

    public QuestionType(String question, String imageURL) {
        this.question = question;
        this.imageURL = imageURL;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
