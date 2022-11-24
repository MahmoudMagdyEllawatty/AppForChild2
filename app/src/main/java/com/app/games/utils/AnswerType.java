package com.app.games.utils;

public class AnswerType {
    private String answer;
    private String imageURL;
    private int correct;

    public AnswerType() {
    }

    public AnswerType(String answer, String imageURL, int correct) {
        this.answer = answer;
        this.imageURL = imageURL;
        this.correct = correct;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
